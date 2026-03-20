import java.util.*;

// Reservation Class
class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Queue (FIFO)
class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    void add(Reservation r) {
        queue.add(r);
    }

    Reservation poll() {
        return queue.poll();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Room Allocation Service
class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.roomType;

        // Check availability
        if (inventory.getAvailability(type) <= 0) {
            System.out.println("No rooms available for " + reservation.guestName);
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(type);

        // Store globally (avoid duplicates)
        allocatedRoomIds.add(roomId);

        // Store by type
        assignedRoomsByType
                .computeIfAbsent(type, k -> new HashSet<>())
                .add(roomId);

        // Update inventory
        inventory.reduceAvailability(type);

        // Confirm booking
        System.out.println("Booking confirmed for Guest: "
                + reservation.guestName + ", Room ID: " + roomId);
    }

    private String generateRoomId(String type) {
        int count = assignedRoomsByType.getOrDefault(type, new HashSet<>()).size() + 1;
        return type + "-" + count;
    }
}

// MAIN CLASS
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        // Create services
        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();
        RoomAllocationService service = new RoomAllocationService();

        // Add booking requests (FIFO)
        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Subha", "Single"));
        queue.add(new Reservation("Vanmathi", "Suite"));

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            service.allocateRoom(r, inventory);
        }
    }
}