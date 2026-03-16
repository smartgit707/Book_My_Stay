import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class UseCase8BookingHistoryReport {

    static List<Reservation> bookingHistory = new ArrayList<>();

    public static void main(String[] args) {

        // Simulating confirmed bookings (as per your output)
        bookingHistory.add(new Reservation("Abhi", "Single"));
        bookingHistory.add(new Reservation("Subha", "Double"));
        bookingHistory.add(new Reservation("Vanmathi", "Suite"));

        System.out.println("Booking History and Reporting");
        System.out.println();
        System.out.println("Booking History Report");

        for (Reservation r : bookingHistory) {
            System.out.println("Guest: " + r.guestName + ", Room Type: " + r.roomType);
        }
    }
}