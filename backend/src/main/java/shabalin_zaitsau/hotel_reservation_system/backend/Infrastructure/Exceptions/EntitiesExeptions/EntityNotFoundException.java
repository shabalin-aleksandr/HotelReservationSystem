package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions;

/**
 * Exception thrown to indicate that an entity or resource was not found.
 * This exception is typically used in situations where an operation
 * expected to find a specific entity in a data source but failed to do so.
*/
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code EntityNotFoundException} with the specified
     * detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
