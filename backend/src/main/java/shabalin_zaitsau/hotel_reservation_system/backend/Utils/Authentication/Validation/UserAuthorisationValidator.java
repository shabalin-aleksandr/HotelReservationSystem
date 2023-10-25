package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.AuthConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.DuplicateUserException;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Events.UserExistByEmailCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IAuthenticationRequest;

/**
 * A service class to validate user authorisation requests before the user is authorised.
 */
@Service
@AllArgsConstructor
public class UserAuthorisationValidator {

    private final ApplicationEventPublisher eventPublisher;

    public void validateAuthorisation(@NotNull IAuthenticationRequest authenticationRequest) {
        if (authenticationRequest.getPassword() == null || authenticationRequest.getPassword().isEmpty()) {
            throw new AuthConflictException("Password cannot be null or empty");
        }
        validateUserExists(authenticationRequest.getEmail());
    }

    private void validateUserExists(String email) {
        UserExistByEmailCheckEvent event = new UserExistByEmailCheckEvent(email);
        eventPublisher.publishEvent(event);
        if (!event.isUserExists()) {
            throw new DuplicateUserException("User with this email doesn't exist");
        }
    }
}
