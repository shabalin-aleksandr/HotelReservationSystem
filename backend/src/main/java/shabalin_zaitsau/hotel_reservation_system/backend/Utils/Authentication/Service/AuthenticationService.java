package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Service;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.Role;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.AuthConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.AuthenticationResponseDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IAuthenticationRequest;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IRegistrationRequest;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Events.UserAuthenticationCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation.MainValidator;

import java.util.Arrays;
import java.util.Objects;

/**
 * Service class responsible for user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher eventPublisher;
    private final MainValidator mainValidator;
    private final Dotenv dotenv = Dotenv.load();

    /**
     * Registers a new user.
     *
     * @param request the registration request
     * @return an {@link AuthenticationResponseDto} containing the JWT token
     */
    public AuthenticationResponseDto register(IRegistrationRequest request) {
        mainValidator.validateRegistration(request);
        var user = User.builder()
                .role(Role.USER)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .country(request.getCountry())
                .region(request.getRegion())
                .city(request.getCity())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Registers a new Admin.
     *
     * @param request the registration request
     * @return an {@link AuthenticationResponseDto} containing the JWT token
     */
    public AuthenticationResponseDto registerAdmin(IRegistrationRequest request) {
        mainValidator.validateRegistration(request);
        String[] superAdminUsers = Objects
                .requireNonNull(dotenv.get("SUPER_ADMIN_USERS"))
                .split(",");
        AdminType adminType = Arrays.asList(superAdminUsers)
                .contains(request.getEmail()) ? AdminType.SUPER_ADMIN : AdminType.HOTEL_MANAGER;
        var adminUser = User.builder()
                .role(Role.ADMIN)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .country(request.getCountry())
                .region(request.getRegion())
                .city(request.getCity())
                .build();
        userRepository.save(adminUser);
        var adminDetails = Admin.builder()
                .userDetails(adminUser)
                .adminType(adminType)
                .build();
        adminRepository.save(adminDetails);
        var jwtToken = jwtService.generateToken(adminUser);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user.
     *
     * @param request the authentication request
     * @return an {@link AuthenticationResponseDto} containing the JWT token
     * @throws AuthConflictException if the provided password is null or empty
     */
    public AuthenticationResponseDto authenticate(IAuthenticationRequest request) {
        mainValidator.validateAuthorisation(request);

        UserAuthenticationCheckEvent authEvent = new UserAuthenticationCheckEvent(
                request.getEmail(), request.getPassword());
        eventPublisher.publishEvent(authEvent);

        UserDetails userDetails = authEvent.getUserDetails();
        if (userDetails == null) {
            throw new AuthConflictException("Unexpected error: User details not found");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new AuthConflictException("Invalid password");
        }

        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
