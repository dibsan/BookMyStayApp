import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

// Reservation class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared booking request queue
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    public Reservation pollRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Shared room inventory
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

    public void reduceAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        System.out.println("Single: " + getAvailability("Single"));
        System.out.println("Double: " + getAvailability("Double"));
        System.out.println("Suite: " + getAvailability("Suite"));
    }
}

// Allocation service
class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Integer> roomTypeCounters;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        roomTypeCounters = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.roomType;

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("No rooms available for Guest: " + reservation.guestName);
            return;
        }

        String roomId = generateRoomId(roomType);

        while (allocatedRoomIds.contains(roomId)) {
            roomId = generateRoomId(roomType);
        }

        allocatedRoomIds.add(roomId);
        inventory.reduceAvailability(roomType);

        System.out.println("Booking confirmed for Guest: "
                + reservation.guestName + ", Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {
        int nextNumber = roomTypeCounters.getOrDefault(roomType, 0) + 1;
        roomTypeCounters.put(roomType, nextNumber);
        return roomType + "-" + nextNumber;
    }
}

// Thread task for concurrent booking
class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;

            // Only one thread can take a request at a time
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.pollRequest();
            }

            // Only one thread can allocate/update inventory at a time
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        // Create booking processor threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );
        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        // Start concurrent processing
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        inventory.displayInventory();
    }
}