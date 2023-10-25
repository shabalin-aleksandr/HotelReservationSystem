package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.AuthConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.DuplicateUserException;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Events.UserExistByEmailCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IRegistrationRequest;

/**
 * A service class to validate user registration requests before the user is registered.
 */
@Service
@AllArgsConstructor
public class UserRegistrationValidator {

    private final ApplicationEventPublisher eventPublisher;
    private final UserDataValidator userDataValidator;

    public void validateRegistration(@NotNull IRegistrationRequest registrationRequest) {
        if (registrationRequest.getPassword() == null || registrationRequest.getPassword().isEmpty()) {
            throw new AuthConflictException("Password cannot be null or empty");
        }

        if (userDataValidator.isEmailValid(registrationRequest.getEmail())) {
            throw new AuthConflictException("Invalid email format");
        }

        if (userDataValidator.isPasswordValid(registrationRequest.getPassword())) {
            throw new AuthConflictException("Invalid password format");
        }
        validateUserExists(registrationRequest.getEmail());
    }

    private void validateUserExists(String email) {
        UserExistByEmailCheckEvent event = new UserExistByEmailCheckEvent(email);
        eventPublisher.publishEvent(event);
        if (event.isUserExists()) {
            throw new DuplicateUserException("A user with this email already exists");
        }
    }
}
