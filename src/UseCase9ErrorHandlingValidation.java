import java.util.*;

// Custom exception for invalid booking attempts
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

public class UseCase9ErrorHandlingValidation {

    // Allowed room types (could be dynamic)
    static final Set<String> VALID_ROOM_TYPES = Set.of("Single", "Double", "Suite");

    static List<Reservation> bookingHistory = new ArrayList<>();

    public static void main(String[] args) {

        // Inventory of rooms
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);

        // Queue for booking requests
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Booking requests — including one invalid room type to demo validation
        bookingQueue.add(new Reservation("Abhi", "Single", null));
        bookingQueue.add(new Reservation("Subha", "Double", null));
        bookingQueue.add(new Reservation("Vanmathi", "Suite", null));
        bookingQueue.add(new Reservation("Ravi", "Penthouse", null)); // Invalid room type
        bookingQueue.add(new Reservation("Sneha", "Single", null));
        bookingQueue.add(new Reservation("Arjun", "Single", null)); // Inventory should be exhausted here

        int roomCounter = 1;

        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.poll();

            try {
                validateBookingRequest(request, inventory);

                // If validation passes, allocate room
                String roomId = request.roomType + "-" + roomCounter++;

                // Update inventory safely
                int currentCount = inventory.get(request.roomType);
                inventory.put(request.roomType, currentCount - 1);

                // Add to booking history
                bookingHistory.add(new Reservation(request.guestName, request.roomType, roomId));

                System.out.println("Reservation Confirmed:");
                System.out.println(request.guestName + " allocated room " + roomId);
                System.out.println();

            } catch (InvalidBookingException e) {
                // Fail fast with clear message but continue processing next bookings
                System.out.println("Reservation Failed for " + request.guestName + ": " + e.getMessage());
                System.out.println();
            }
        }

        // Print booking history report
        printBookingHistoryReport();
    }

    // Validation method to check booking request correctness
    static void validateBookingRequest(Reservation request, HashMap<String, Integer> inventory) throws InvalidBookingException {
        if (request.roomType == null || request.roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (!VALID_ROOM_TYPES.contains(request.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + request.roomType);
        }

        if (!inventory.containsKey(request.roomType)) {
            throw new InvalidBookingException("Room type not found in inventory: " + request.roomType);
        }

        if (inventory.get(request.roomType) <= 0) {
            throw new InvalidBookingException("No " + request.roomType + " rooms available");
        }
    }

    static void printBookingHistoryReport() {
        System.out.println("Booking History and Reporting\n");
        System.out.println("Booking History Report");

        for (Reservation r : bookingHistory) {
            System.out.println("Guest: " + r.guestName + ", Room Type: " + r.roomType);
        }
    }
}