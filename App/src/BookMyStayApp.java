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

// Room Types
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// Inventory Class (State Holder)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // example unavailable
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Search Service (READ-ONLY)
class SearchService {

    void searchAvailableRooms(Room[] rooms, RoomInventory inventory) {
        System.out.println("---- Available Rooms ----");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.type);

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails(available);
            }
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Create Room Objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Inventory (centralized state)
        RoomInventory inventory = new RoomInventory();

        // Search Service (read-only)
        SearchService searchService = new SearchService();

        // Guest searches for rooms
        searchService.searchAvailableRooms(rooms, inventory);
    }
}