package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions;

/**
 * Exception thrown when there is a conflict with a user.
 */
public class UserConflictException extends RuntimeException {
    /**
     * Constructs a new UserConflictException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserConflictException(String message) {
        super(message);
    }
}
