package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.AuthenticationRequestDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.AuthenticationResponseDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.RegistrationRequestDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Service.AuthenticationService;

/**
 * The {@link AuthenticationController} class handles HTTP requests related to user authentication and registration.
 * Controller with endpoints mapped to {@code "/api/auth"} path.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    /**
     * An instance of the {@link AuthenticationService} class used to handle authentication and registration requests.
     */
    private final AuthenticationService service;

    /**
     * Processes a registration request and returns a response with a JWT token.
     * The request body should contain user registration data in the {@link RegistrationRequestDto} format.
     *
     * @param request A {@link RegistrationRequestDto} object containing user registration data.
     * @return A {@link ResponseEntity} object with the HTTP status code and an {@link AuthenticationResponseDto} object
     * containing a JWT token.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegistrationRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AuthenticationResponseDto> registerAdmin(
            @RequestBody RegistrationRequestDto request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    /**
     * Processes an authentication request and returns a response with a JWT token.
     * The request body should contain user authentication data in the {@link AuthenticationRequestDto} format.
     *
     * @param request A {@link AuthenticationRequestDto} object containing user authentication data.
     * @return A {@link ResponseEntity} object with the HTTP status code and an {@link AuthenticationResponseDto} object
     * containing a JWT token.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
