import java.util.*;

// Reservation Class (represents booking request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " requested " + roomType);
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display all requests in order
    public void displayRequests() {
        System.out.println("\n--- Booking Request Queue (FIFO Order) ---");
        for (Reservation r : requestQueue) {
            r.display();
        }
    }
}

// Main Class
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Simulate guest booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        // Display queue (FIFO order preserved)
        queue.displayRequests();
    }
}