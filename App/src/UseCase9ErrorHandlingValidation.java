import java.util.HashMap;
import java.util.Map;

// Custom Exception for invalid booking cases
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }
}

// Validator class
class ReservationValidator {

    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory
    ) throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for room type: " + roomType);
        }
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {

        System.out.println("Error Handling and Validation");

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        String[][] bookingInputs = {
                {"Abhi", "Single"},
                {"", "Double"},
                {"Subha", "Deluxe"},
                {"Vanmathi", "Suite"}
        };

        for (String[] input : bookingInputs) {
            String guestName = input[0];
            String roomType = input[1];

            try {
                validator.validate(guestName, roomType, inventory);
                System.out.println("Valid booking input for Guest: " + guestName + ", Room Type: " + roomType);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }
    }
}