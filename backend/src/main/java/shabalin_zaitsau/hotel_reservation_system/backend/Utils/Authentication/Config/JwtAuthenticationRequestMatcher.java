package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.regex.Pattern;

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
    private static final List<Pattern> EXCLUDED_PATHS = List.of(
            Pattern.compile("/api/auth/.*"),
            Pattern.compile("/api/users"),
            Pattern.compile("/api/hotels"),
            Pattern.compile("/api/images"),
            //Pattern.compile("/api/rooms"),
            Pattern.compile("/api/reservations"),
            Pattern.compile("/api/amenities"),
            Pattern.compile("/api/users/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"),
            Pattern.compile("/api/rooms/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"),
            Pattern.compile("/swagger-ui/.*"),
            Pattern.compile("/swagger-ui/index.html/.*"),
            Pattern.compile("/api-docs/.*"),
            Pattern.compile("/api-docs"),
            Pattern.compile("/swagger-resources/.*"),
            Pattern.compile("/v2/api-docs.*"),
            Pattern.compile("/v3/api-docs.*"),
            Pattern.compile("/webjars/.*")
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
        return EXCLUDED_PATHS.stream().anyMatch(pattern -> pattern.matcher(requestUrl).matches());
    }
}
