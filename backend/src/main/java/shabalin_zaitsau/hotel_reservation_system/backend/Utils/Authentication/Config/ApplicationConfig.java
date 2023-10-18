package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.UnauthorizedException;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Events.UserExistByEmailCheckEvent;

/**
 * This class is a Spring configuration class used for setting up the application's beans.
 * It contains methods that define beans for user details service, authentication provider,
 * authentication manager and password encoder.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Defines a bean for the user details service.
     *
     * @return a user details service object that finds a user in the database by email and returns a UserDetails object.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userEmail -> {
            UserExistByEmailCheckEvent event = new UserExistByEmailCheckEvent(userEmail);
            eventPublisher.publishEvent(event);
            if (event.isUserExists()) {
                return event.getUserDetails();
            } else {
                throw new UnauthorizedException("Authentication is required");
            }
        };
    }

    /**
     * Defines a bean for the authentication provider.
     *
     * @return an authentication provider object that uses the user details service bean and a password encoder bean
     * for user authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Defines a bean for the authentication manager.
     *
     * @param config the authentication configuration object.
     * @return an authentication manager object that manages the authentication process for the application.
     * @throws Exception if an error occurs while getting the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(@NotNull AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines a bean for the password encoder.
     *
     * @return a password encoder object that encodes and decodes passwords for user authentication.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The JwtAuthenticationRequestMatcher class is responsible for matching incoming HTTP requests
     * against a list of excluded paths. Requests that match one of the excluded paths are considered
     * non-secured and will not be authenticated with a JWT token.
     *
     * @return an instance of {@link JwtAuthenticationRequestMatcher} to be used for matching incoming HTTP requests
     */
    @Bean
    public JwtAuthenticationRequestMatcher jwtAuthenticationRequestMatcher() {
        return new JwtAuthenticationRequestMatcher();
    }
}
