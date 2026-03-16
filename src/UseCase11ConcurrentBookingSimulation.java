import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;

public class UseCase11ConcurrentBookingSimulation {

    // --- Inner Class: Reservation Data Model ---
    static class Reservation {
        String guestName;
        String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    // --- Inner Class: Shared Booking Queue ---
    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void enqueue(Reservation res) { queue.add(res); }
        public Reservation dequeue() { return queue.poll(); }
        public boolean isEmpty() { return queue.isEmpty(); }
    }

    // --- Inner Class: Shared Room Inventory ---
    static class RoomInventory {
        private Map<String, Integer> counts = new HashMap<>();

        public RoomInventory() {
            counts.put("Single", 5);
            counts.put("Double", 3);
            counts.put("Suite", 2);
        }

        public int getCount(String type) { return counts.getOrDefault(type, 0); }
        public void updateCount(String type, int newCount) { counts.put(type, newCount); }

        public void display() {
            System.out.println("Single: " + counts.get("Single"));
            System.out.println("Double: " + counts.get("Double"));
            System.out.println("Suite: " + counts.get("Suite"));
        }
    }

    // --- Inner Class: Allocation Logic ---
    static class RoomAllocationService {
        public void allocateRoom(Reservation res, RoomInventory inventory) {
            int current = inventory.getCount(res.roomType);
            if (current > 0) {
                inventory.updateCount(res.roomType, current - 1);
                // Simple ID logic: (Initial - Remaining)
                String roomId = res.roomType + "-1";
                System.out.println("Booking confirmed for Guest: " + res.guestName + ", Room ID: " + roomId);
            }
        }
    }

    // --- Inner Class: The Threaded Processor ---
    static class ConcurrentBookingProcessor implements Runnable {
        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(BookingRequestQueue bq, RoomInventory inv, RoomAllocationService ras) {
            this.bookingQueue = bq;
            this.inventory = inv;
            this.allocationService = ras;
        }

        @Override
        public void run() {
            while (true) {
                Reservation reservation = null;

                // Synchronize on queue to fetch next request
                synchronized (bookingQueue) {
                    if (bookingQueue.isEmpty()) break;
                    reservation = bookingQueue.dequeue();
                }

                if (reservation != null) {
                    // Synchronize on inventory to prevent double booking
                    synchronized (inventory) {
                        allocationService.allocateRoom(reservation, inventory);
                    }
                }
            }
        }
    }

    // --- Main Entry Point ---
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Adding requests based on your output requirement
        bookingQueue.enqueue(new Reservation("Abhi", "Single"));
        bookingQueue.enqueue(new Reservation("Vanmathi", "Double"));
        bookingQueue.enqueue(new Reservation("Kural", "Suite"));
        bookingQueue.enqueue(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\nRemaining Inventory:");
        inventory.display();
    }
}