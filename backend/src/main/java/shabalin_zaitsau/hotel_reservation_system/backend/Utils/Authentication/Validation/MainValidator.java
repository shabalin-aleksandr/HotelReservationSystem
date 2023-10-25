package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IAuthenticationRequest;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IRegistrationRequest;

@Service
@AllArgsConstructor
public class MainValidator {

    private final UserRegistrationValidator userRegistrationValidator;
    private final UserAuthorisationValidator userAuthorisationValidator;

    public void validateRegistration(IRegistrationRequest registrationRequest) {
        userRegistrationValidator.validateRegistration(registrationRequest);
    }

    public void validateAuthorisation(IAuthenticationRequest authenticationRequest) {
        userAuthorisationValidator.validateAuthorisation(authenticationRequest);
    }
}
