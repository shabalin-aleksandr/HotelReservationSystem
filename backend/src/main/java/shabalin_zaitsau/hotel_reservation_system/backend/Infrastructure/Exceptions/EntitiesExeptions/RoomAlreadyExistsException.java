package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions;

/**
 * Exception class representing an exception that is thrown when attempting
 * to create a room that already exists in the application.
 *
 * <p>
 * This exception should be used to indicate that a new room cannot be created
 * because a room with the same identifier or properties already exists in
 * the system. The exception message can provide additional details about the
 * nature of the conflict.
 * </p>
 *
 * @see RuntimeException
 */
public class RoomAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code RoomAlreadyExistsException} with the specified
     * detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}

