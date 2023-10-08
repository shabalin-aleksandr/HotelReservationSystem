package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.GlobalHendler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.InvalidRatingInputException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.RoomAlreadyExistsException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.UserConflictException;

import java.util.HashMap;
import java.util.Map;

/**
 * A global exception handler for the application.
 * <p>
 * This class handles exceptions that may occur during the application's execution and returns
 * appropriate error responses to the clients.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link UserConflictException} exception and returns a conflict response to the client.
     *
     * @param ex the UserConflictException exception to handle.
     * @return a conflict response with an error message.
     */
    @ExceptionHandler(UserConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleUserConflictException(UserConflictException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Handles EntityNotFoundException and returns a response with an error message and HTTP status code 404.
     *
     * @param ex The EntityNotFoundException to be handled
     * @return A ResponseEntity containing a map with an error message and HTTP status code 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler method for handling {@link InvalidRatingInputException}.
     * <p>
     * This method is responsible for processing exceptions of type
     * {@code InvalidRatingInputException} and returning an appropriate
     * {@link ResponseEntity} with an error message and a status code indicating
     * a bad request (HTTP 400).
     * </p>
     *
     * @param ex The {@code InvalidRatingInputException} that was thrown.
     * @return A {@link ResponseEntity} containing an error message and a bad request
     *         status code.
     *
     * @see InvalidRatingInputException
     */
    @ExceptionHandler(InvalidRatingInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleInvalidRatingInputException(InvalidRatingInputException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler method to handle {@link RoomAlreadyExistsException}.
     *
     * <p>
     * This method is invoked when a {@link RoomAlreadyExistsException} is thrown within
     * the application. It returns a response entity containing an error message and
     * a status code indicating a bad request (HTTP 400). The error message provides
     * details about why the room creation failed due to an existing room.
     * </p>
     *
     * @param ex The {@link RoomAlreadyExistsException} that was thrown.
     * @return A response entity containing an error message and HTTP 400 status code.
     *
     * @see RoomAlreadyExistsException
     * @see ResponseEntity
     * @see HttpStatus
     */
    @ExceptionHandler(RoomAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleRoomAlreadyExistsException(RoomAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
