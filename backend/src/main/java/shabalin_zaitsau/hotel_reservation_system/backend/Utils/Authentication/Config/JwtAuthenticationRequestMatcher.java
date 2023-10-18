package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

/**
 * This class implements the Spring Security RequestMatcher interface
 * to determine if a given request requires JWT authentication.
 * Requests to the paths specified in the EXCLUDED_PATHS list are excluded from
 * JWT authentication.
 */
public class JwtAuthenticationRequestMatcher implements RequestMatcher {

    /**
     * A list of paths that are excluded from JWT authentication.
     */
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/auth/",
            "/api/public/"
    );

    /**
     * Matches a given request to determine if JWT authentication is required.
     *
     * @param request the HttpServletRequest to be matched
     * @return true if the request requires JWT authentication, false otherwise
     */
    @Override
    public boolean matches(@NotNull HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        return EXCLUDED_PATHS.stream()
                .noneMatch(requestUrl::startsWith);
    }
}
