package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Service;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.Role;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * The {@link JwtService} class provides methods for generating, validating, and extracting user information
 * from JWT tokens.
 */
@Service
@AllArgsConstructor
public class JwtService {

    /**
     * The secret key used for signing JWT tokens.
     */
    private static final Dotenv dotenv = Dotenv.load();
    private final String SECRET_KEY = dotenv.get("JWT_SECRET_KEY");
    private AdminRepository adminRepository;

    /**
     * Extracts the user email from the provided JWT token.
     *
     * @param jwtToken the JWT token from which to extract the user email
     * @return the user email extracted from the JWT token
     */
    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public UUID extractUserId(String jwtToken) {
        return UUID.fromString(extractClaim(jwtToken, claims -> claims.get("id", String.class)));
    }

    /**
     * Extracts the claim identified by the specified resolver function from the provided JWT token.
     *
     * @param jwtToken the JWT token from which to extract the claim
     * @param claimsResolver the function that identifies the claim to extract
     * @param <T> the type of the claim to extract
     * @return the claim extracted from the JWT token
     */
    public <T> T extractClaim(String jwtToken, @NotNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the specified user details.
     *
     * @param userDetails the user details for which to generate the JWT token
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateTokenPayload(userDetails);
    }

    /**
     * Generates a JWT token payload for the given user details.
     *
     * @param userDetails The UserDetails representing the user.
     * @return A JWT token payload containing user claims.
     */
    private String generateTokenPayload(@NotNull UserDetails userDetails) {
        User user = (User) userDetails;

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getUserId().toString());

        if (user.getRole() == Role.ADMIN) {
            Admin admin = adminRepository.findAdminByUserDetails(user);
            if (admin != null) {
                claims.put("adminId", admin.getAdminId().toString());
            }
        }

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks whether the specified JWT token is valid for the specified user details.
     *
     * @param jwtToken the JWT token to validate
     * @param userDetails the user details to validate against the JWT token
     * @return true if the JWT token is valid for the specified user details, otherwise false
     */
    public boolean isTokenValid(String jwtToken, @NotNull UserDetails userDetails) {
        final String userEmail = extractUserEmail(jwtToken);
        return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    /**
     * Checks whether the specified JWT token has expired.
     *
     * @param jwtToken the JWT token to check for expiration
     * @return true if the JWT token has expired, otherwise false
     */
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    /**
     * Extracts the expiration date of the JWT token.
     *
     * @param jwtToken the JWT token to extract the expiration date from
     * @return the expiration date of the JWT token
     */
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Extracts all claims (payload) from the provided JWT token.
     *
     * @param jwtToken the JWT token to extract the claims from
     * @return all claims (payload) from the JWT token
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Returns the signing key used for generating and validating JWT tokens.
     *
     * @return the signing key
     */
    @NotNull
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
