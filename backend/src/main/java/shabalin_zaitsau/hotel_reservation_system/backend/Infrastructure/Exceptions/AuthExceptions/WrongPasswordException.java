package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions;

/**
 * Exception to be thrown when a user attempts to log in with an incorrect password.
 */
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
