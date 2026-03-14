import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Inventory of rooms
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);

        // Queue for booking requests (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Guest1", "Single"));
        bookingQueue.add(new Reservation("Guest2", "Double"));
        bookingQueue.add(new Reservation("Guest3", "Single"));
        bookingQueue.add(new Reservation("Guest4", "Suite"));

        // Store allocated room IDs
        HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());

        int roomCounter = 1;

        while (!bookingQueue.isEmpty()) {

            Reservation request = bookingQueue.poll();
            String type = request.roomType;

            if (inventory.get(type) > 0) {

                String roomId = type + "-R" + roomCounter++;

                allocatedRooms.get(type).add(roomId);

                inventory.put(type, inventory.get(type) - 1);

                System.out.println("Reservation Confirmed:");
                System.out.println(request.guestName + " allocated room " + roomId);
                System.out.println();

            } else {

                System.out.println("Reservation Failed for "
                        + request.guestName + " (No " + type + " rooms available)");
                System.out.println();
            }
        }
    }
}