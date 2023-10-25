package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions;

/**
 * Exception to be thrown when a user attempts to perform an action without being authorized.
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
