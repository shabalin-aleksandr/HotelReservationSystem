package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Service.JwtService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtAuthenticationFilter is responsible for filtering incoming requests, extracting and validating JWT tokens from the
 * "Authorization" header of the HTTP request. If a valid token is present, it authenticates the user by setting the
 * user's authentication in the SecurityContext.
 */
@Component
@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationRequestMatcher jwtAuthenticationRequestMatcher;

    /**
     * Filters incoming HTTP requests and extracts and validates JWT tokens from the "Authorization" header.
     * If a valid token is present, it authenticates the user by setting the user's authentication
     * in the SecurityContext.
     *
     * @param request the incoming HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to execute
     * @throws ServletException if there is a Servlet-related problem
     * @throws IOException if there is an I/O problem
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Get Authorization header and extract JWT token
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // Check if Authorization header is not present or does not start with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleMissingJwtToken(response);
            return;
        }

        try {
            // Extract the JWT token
            jwtToken = authHeader.substring(7);

            // Extract the user email from the JWT token
            userEmail = jwtService.extractUserEmail(jwtToken);

            // Check if user is not already authenticated
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details by email
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Validate the JWT token
                if (jwtService.isTokenValid(jwtToken, userDetails)) {

                    // Create authentication token and set it in the security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (SignatureException e) {
            sendErrorResponse(response, "Invalid token");
            return;
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, "Token has expired");
            return;
        } catch (MalformedJwtException e) {
            sendErrorResponse(response, "Malformed JWT token");
            return;
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return !jwtAuthenticationRequestMatcher.matches(request);
    }

    /**
     * Handles the situation where a JWT token is missing.
     * Sets the HTTP response status code to SC_UNAUTHORIZED and returns an error message as JSON.
     *
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while writing the error message to the output stream
     */
    private void handleMissingJwtToken(HttpServletResponse response) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Missing JWT token");

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    /**
     * Sends an HTTP error response with the given error message.
     *
     * @param response the HTTP servlet response
     * @param errorMessage the error message to send
     * @throws IOException if an I/O error occurs while writing the error message to the output stream
     */
    private void sendErrorResponse(HttpServletResponse response,
                                   String errorMessage) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", errorMessage);
        String errorJson = new ObjectMapper().writeValueAsString(errorMap);
        response.getWriter().write(errorJson);
    }
}
