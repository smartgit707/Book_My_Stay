import java.util.HashMap;
import java.util.Map;

// 1. Reuse your Room classes from UC2 here (SingleRoom, DoubleRoom, etc.)

// 2. New Class: Centralized Inventory Management
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        // Initializing room counts in the constructor
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0); // O(1) Lookup
    }

    public void displayInventory() {
        System.out.println("--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

// 3. Main Application Class
public class UseCase3HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Welcome to Book_My_Stay (Version 3.0) ---");

        RoomInventory hotelInventory = new RoomInventory();
        hotelInventory.displayInventory();

        System.out.println("\nApplication terminated successfully.");
    }
}