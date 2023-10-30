package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.AuthConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.SamePasswordException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.UnauthorizedException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.WrongPasswordException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.*;

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
    public ResponseEntity<Map<String, String>> handleUserConflictException(
            @NotNull UserConflictException ex
    ) {
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
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(
            @NotNull EntityNotFoundException ex
    ) {
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
    public ResponseEntity<Map<String, String>> handleInvalidRatingInputException(
            @NotNull InvalidRatingInputException ex
    ) {
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
    public ResponseEntity<Map<String, String>> handleRoomAlreadyExistsException(
            @NotNull RoomAlreadyExistsException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the exception thrown when a user is unauthorized to perform a certain action.
     *
     * @param ex: the UnauthorizedException that was thrown
     * @return a ResponseEntity containing the error message and status code 401 UNAUTHORIZED
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedException(
            @NotNull UnauthorizedException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles the exception thrown when the user enters a wrong password.
     *
     * @param ex: the WrongPasswordException that was thrown
     * @return a ResponseEntity containing the error message and status code 401 UNAUTHORIZED
     */
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Map<String, String>> handleWrongPasswordException(
            @NotNull WrongPasswordException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles the exception thrown when the user enters the same password again.
     *
     * @param ex: the SamePasswordException that was thrown
     * @return a ResponseEntity containing the error message and status code 400 BAD REQUEST
     */
    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<Map<String, String>> handleSamePasswordException(
            @NotNull SamePasswordException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for {@link AuthConflictException}.
     * Which appears when arguments in request body are invalid.
     *
     * @param ex The exception object to handle.
     * @return A ResponseEntity object containing the error message and status code CONFLICT.
     */
    @ExceptionHandler(AuthConflictException.class)
    public ResponseEntity<Map<String, String>> handleAuthConflictException(
            @NotNull AuthConflictException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * This method handles the DuplicateUserException and returns an appropriate HTTP response with a message
     * containing the error.
     *
     * @param ex the DuplicateUserException thrown
     * @return a ResponseEntity object containing the error message and HTTP status code
     */
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateUserException(
            @NotNull DuplicateUserException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


    /**
     * Handles the exception that occurs when the maximum upload size for a file is exceeded.
     *
     * @return A ResponseEntity containing an error message and a HTTP BAD REQUEST status code.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleMaxSizeException() {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unable to upload. File is too large!");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the exception that occurs when a required Servlet request part is missing, such as a user avatar.
     *
     * @return A ResponseEntity containing an error message and a HTTP CONFLICT status code.
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestPartException() {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid value for the user avatar. The avatar can't be null");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Handles the exception that occurs when an unexpected error happens during multipart file upload.
     *
     * @return A ResponseEntity containing an error message and a HTTP CONFLICT status code.
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Map<String, String>> handleMultipartException() {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unable to upload. Unexpected error!");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
