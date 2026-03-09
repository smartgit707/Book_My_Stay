// Save this entire block in a file named: UseCase2RoomInitialization.java

// 1. ABSTRACT CLASS (The Blueprint)
// Encapsulation: Fields are private, accessed via getters.
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() { return roomType; }
    public int getNumberOfBeds() { return numberOfBeds; }
    public double getPricePerNight() { return pricePerNight; }

    // Abstract method: Each room type must define its own description
    public abstract void displayDescription();
}

// 2. CONCRETE CLASSES (Inheritance)
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500.0);
    }
    @Override
    public void displayDescription() {
        System.out.println("Description: Ideal for solo travelers, compact and cozy.");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500.0);
    }
    @Override
    public void displayDescription() {
        System.out.println("Description: Perfect for couples, features a king-sized bed.");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }
    @Override
    public void displayDescription() {
        System.out.println("Description: Luxury suite with a separate living area.");
    }
}

// 3. EXECUTION CLASS (The entry point)
public class UseCase2HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("=== Book_My_Stay: Use Case 2 (Room Initialization) ===");
        System.out.println("Version: 2.0\n");

        // Static Availability Representation (Simple variables as requested)
        int singleAvailability = 10;
        int doubleAvailability = 5;
        int suiteAvailability = 2;

        // Polymorphism: Creating an array of the abstract type 'Room'
        Room[] hotelRooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Displaying Details
        for (Room room : hotelRooms) {
            System.out.println("Type: " + room.getRoomType());
            System.out.println("Beds: " + room.getNumberOfBeds());
            System.out.println("Price: ₹" + room.getPricePerNight());
            room.displayDescription();

            // Mapping static availability to the objects
            if (room instanceof SingleRoom) {
                System.out.println("Current Availability: " + singleAvailability);
            } else if (room instanceof DoubleRoom) {
                System.out.println("Current Availability: " + doubleAvailability);
            } else if (room instanceof SuiteRoom) {
                System.out.println("Current Availability: " + suiteAvailability);
            }
            System.out.println("------------------------------------------------");
        }

        System.out.println("Execution Completed.");
    }
}