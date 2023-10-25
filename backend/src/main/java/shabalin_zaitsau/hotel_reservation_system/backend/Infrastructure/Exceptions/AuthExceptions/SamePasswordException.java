package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions;

/**
 * Exception to be thrown when a user attempts to set a password that is the same as their current password.
 */
public class SamePasswordException extends RuntimeException {
    public SamePasswordException(String message) {
        super(message);
    }
}
