import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println(guestName + " requested " + roomType + " room");
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Guest1", "Single"));
        bookingQueue.add(new Reservation("Guest2", "Suite"));
        bookingQueue.add(new Reservation("Guest3", "Double"));

        System.out.println("Booking Requests in Queue:\n");

        for (Reservation r : bookingQueue) {
            r.display();
        }
    }
}