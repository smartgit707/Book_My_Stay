import java.io.*;
import java.util.*;

public class UseCase12DataPersistenceRecovery {

    // --- Inner Class: Room Inventory ---
    static class RoomInventory {
        private Map<String, Integer> counts = new HashMap<>();

        public RoomInventory() {
            // Default counts if no file is found
            counts.put("Single", 5);
            counts.put("Double", 3);
            counts.put("Suite", 2);
        }

        public Map<String, Integer> getAll() { return counts; }

        public void updateCount(String type, int count) {
            counts.put(type, count);
        }

        public void display() {
            System.out.println("Current Inventory:");
            System.out.println("Single: " + counts.getOrDefault("Single", 0));
            System.out.println("Double: " + counts.getOrDefault("Double", 0));
            System.out.println("Suite: " + counts.getOrDefault("Suite", 0));
        }
    }

    // --- Inner Class: File Persistence Service ---
    static class FilePersistenceService {

        /**
         * Saves room inventory state to a file.
         * Format: roomType=availableCount
         */
        public void saveInventory(RoomInventory inventory, String filePath) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
                    writer.println(entry.getKey() + "=" + entry.getValue());
                }
                System.out.println("Inventory saved successfully.");
            } catch (IOException e) {
                System.err.println("Error saving inventory: " + e.getMessage());
            }
        }

        /**
         * Loads room inventory state from a file.
         */
        public void loadInventory(RoomInventory inventory, String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        inventory.updateCount(parts[0], Integer.parseInt(parts[1]));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error loading inventory. Starting with defaults.");
            }
        }
    }

    // --- Main Entry Point ---
    public static void main(String[] args) {
        System.out.println("System Recovery");

        String persistenceFile = "inventory_state.txt";
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        // 1. Attempt to load existing state
        persistenceService.loadInventory(inventory, persistenceFile);

        // 2. Display the state (recovered or fresh)
        System.out.println("");
        inventory.display();

        // 3. Save the current state for next time
        persistenceService.saveInventory(inventory, persistenceFile);
    }
}