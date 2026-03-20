import java.io.*;
import java.util.HashMap;
import java.util.Map;

// RoomInventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void setAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        System.out.println("Single: " + getAvailability("Single"));
        System.out.println("Double: " + getAvailability("Double"));
        System.out.println("Suite: " + getAvailability("Suite"));
    }
}

// FilePersistenceService class
class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save inventory data.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean validDataFound = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    String roomType = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    inventory.setAvailability(roomType, count);
                    validDataFound = true;
                }
            }

            if (!validDataFound) {
                System.out.println("No valid inventory data found. Starting fresh.");
            }
        } catch (Exception e) {
            System.out.println("No valid inventory data found. Starting fresh.");
        }
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        String filePath = "inventory.txt";

        // Load inventory from file on startup
        persistenceService.loadInventory(inventory, filePath);

        System.out.println();
        inventory.displayInventory();

        // Save inventory to file before shutdown
        persistenceService.saveInventory(inventory, filePath);
        System.out.println("Inventory saved successfully.");
    }
}