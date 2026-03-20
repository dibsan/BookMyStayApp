import java.util.HashMap;

// Abstract Room Class
abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void displayDetails(int availability) {
        System.out.println(type + " - Beds: " + beds +
                ", Price: " + price +
                ", Available: " + availability);
    }
}

// Single Room
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

// Double Room
class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

// Suite Room
class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// Inventory Class using HashMap
class RoomInventory {
    private HashMap<String, Integer> inventory;

    // Constructor (initialize availability)
    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    int getAvailability(String roomType) {
        return inventory.get(roomType);
    }

    // Update availability
    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display inventory
    void displayInventory() {
        for (String key : inventory.keySet()) {
            System.out.println(key + " Available: " + inventory.get(key));
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Create Room Objects (Polymorphism)
        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Create Inventory
        RoomInventory inventory = new RoomInventory();

        // Display Room Details with Availability
        single.displayDetails(inventory.getAvailability("Single Room"));
        doub.displayDetails(inventory.getAvailability("Double Room"));
        suite.displayDetails(inventory.getAvailability("Suite Room"));

        System.out.println("\n--- Inventory ---");
        inventory.displayInventory();

        // Update Example
        inventory.updateAvailability("Single Room", 4);

        System.out.println("\n--- Updated Inventory ---");
        inventory.displayInventory();
    }
}