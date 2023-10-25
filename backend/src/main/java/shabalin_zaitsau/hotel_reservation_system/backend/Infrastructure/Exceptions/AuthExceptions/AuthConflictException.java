package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions;

/**
 * Exception thrown when there is a conflict with authentication or authorization.
 */
public class AuthConflictException extends IllegalArgumentException {
    /**
     * Constructs a new AuthConflictException with the specified detail message.
     *
     * @param message the detail message.
     */
    public AuthConflictException(String message) {
        super(message);
    }
}
