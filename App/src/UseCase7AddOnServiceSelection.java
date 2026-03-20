import java.util.*;

// Represents a confirmed reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType);
    }
}

// Represents one optional service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " - Cost: " + cost);
    }
}

// Manages mapping of reservation -> selected services
class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Attach a service to a reservation
    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service '" + service.getServiceName()
                + "' to Reservation ID: " + reservationId);
    }

    // Display all services for a reservation
    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);

        System.out.println("\nServices for Reservation ID: " + reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService service : services) {
            service.displayService();
        }
    }

    // Calculate total add-on cost
    public double calculateTotalAdditionalCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        double total = 0.0;

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }

        return total;
    }
}

// Main class
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        // Existing reservation
        Reservation reservation = new Reservation("RES101", "Abhi", "Single");
        reservation.displayReservation();

        // Add-on manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects optional services
        manager.addServiceToReservation("RES101", new AddOnService("Breakfast", 500));
        manager.addServiceToReservation("RES101", new AddOnService("WiFi", 200));
        manager.addServiceToReservation("RES101", new AddOnService("Airport Pickup", 1000));

        // Display selected services
        manager.displayServices("RES101");

        // Show total additional cost
        double totalCost = manager.calculateTotalAdditionalCost("RES101");
        System.out.println("\nTotal Additional Cost: " + totalCost);
    }
}