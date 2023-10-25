package shabalin_zaitsau.hotel_reservation_system.backend.Utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;

import java.util.UUID;

/**
 * A utility class that provides methods for accessing information about the currently authenticated user.
 */
public class SecurityUtils {

    /**
     * Returns the ID of the currently authenticated user.
     *
     * @return the ID of the currently authenticated user
     * @throws NullPointerException if no user is authenticated
     */
    public static UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((User) auth.getPrincipal()).getUserId();
    }

    /**
     * Returns the Role of the currently authenticated user.
     *
     * @return the Role of the currently authenticated user
     * @throws NullPointerException if no user is authenticated
     */
    public static String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((User) auth.getPrincipal()).getRole().toString();
    }
}
