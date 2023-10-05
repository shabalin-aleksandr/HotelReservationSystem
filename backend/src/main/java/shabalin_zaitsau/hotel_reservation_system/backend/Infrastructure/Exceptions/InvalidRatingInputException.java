package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions;

/**
 * Exception thrown to indicate that an invalid rating input was provided.
 * This exception is typically used when a rating value is outside the acceptable
 * range or does not meet certain criteria.
 */
public class InvalidRatingInputException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidRatingInputException} with the specified
     * detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     */
    public InvalidRatingInputException(String message) {
        super(message);
    }
}

