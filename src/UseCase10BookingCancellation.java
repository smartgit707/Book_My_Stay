import java.util.*;

// Custom exception for invalid operations
class BookingException extends Exception {
    public BookingException(String message) {
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

public class UseCase10BookingCancellation {

    static HashMap<String, Integer> inventory = new HashMap<>();
    static HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
    static List<Reservation> bookingHistory = new ArrayList<>();
    static Stack<String> releasedRoomIds = new Stack<>();

    public static void main(String[] args) {

        // Initialize inventory
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());

        // Confirmed bookings
        bookingHistory.add(new Reservation("Abhi", "Single", "Single-1"));
        allocatedRooms.get("Single").add("Single-1");
        inventory.put("Single", inventory.get("Single") - 1);

        bookingHistory.add(new Reservation("Subha", "Double", "Double-2"));
        allocatedRooms.get("Double").add("Double-2");
        inventory.put("Double", inventory.get("Double") - 1);

        bookingHistory.add(new Reservation("Vanmathi", "Suite", "Suite-3"));
        allocatedRooms.get("Suite").add("Suite-3");
        inventory.put("Suite", inventory.get("Suite") - 1);

        // Print initial booking history
        System.out.println("Initial Booking History:");
        printBookingHistory();
        System.out.println();

        // Cancellation requests
        cancelBooking("Subha");        // Valid cancellation
        cancelBooking("Ravi");         // Invalid guest (not booked)
        cancelBooking("Abhi");         // Valid cancellation

        // Final booking history after cancellations
        System.out.println("Booking History after Cancellations:");
        printBookingHistory();

        // Print current inventory
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }

    // Method to cancel booking
    static void cancelBooking(String guestName) {
        try {
            Reservation resToCancel = null;
            for (Reservation r : bookingHistory) {
                if (r.guestName.equals(guestName)) {
                    resToCancel = r;
                    break;
                }
            }

            if (resToCancel == null) {
                throw new BookingException("No booking found for guest: " + guestName);
            }

            // Rollback: release room ID and update inventory
            releasedRoomIds.push(resToCancel.roomId);
            allocatedRooms.get(resToCancel.roomType).remove(resToCancel.roomId);
            inventory.put(resToCancel.roomType, inventory.get(resToCancel.roomType) + 1);

            // Remove from booking history
            bookingHistory.remove(resToCancel);

            System.out.println("Cancellation Successful for guest: " + guestName +
                    " (Room released: " + resToCancel.roomId + ")");
        } catch (BookingException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }
    }

    // Print booking history
    static void printBookingHistory() {
        for (Reservation r : bookingHistory) {
            System.out.println("Guest: " + r.guestName + ", Room Type: " + r.roomType + ", Room ID: " + r.roomId);
        }
    }
}