package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions;

/**
 * Exception that is thrown when attempting to add a duplicate user to the system.
 */
public class DuplicateUserException extends RuntimeException {
    /**
     * Constructs a new DuplicateUserException with the specified detail message.
     * @param message the detail message.
     */
    public DuplicateUserException(String message) {
        super(message);
    }
}
