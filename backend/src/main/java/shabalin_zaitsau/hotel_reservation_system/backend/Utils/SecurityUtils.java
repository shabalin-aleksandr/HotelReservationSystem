package shabalin_zaitsau.hotel_reservation_system.backend.Utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;

import java.util.Optional;
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
     * Retrieves the unique identifier (UUID) of the current admin associated with the authenticated user.
     *
     * @param adminRepository The repository used for database operations related to admin entities.
     * @return The UUID of the current admin.
     * @throws AccessDeniedException If the current user is not associated with any admin.
     * @throws IllegalArgumentException If the provided {@code adminRepository} is null.
     */
    public static UUID getCurrentAdminId(@NotNull AdminRepository adminRepository) {
        UUID userId = getCurrentUserId();
        Optional<Admin> adminOptional = adminRepository.findByUserDetailsUserId(userId);
        if (adminOptional.isPresent()) {
            return adminOptional.get().getAdminId();
        } else {
            throw new AccessDeniedException("Current user is not associated with any admin.");
        }
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

    /**
     * Returns the Type of the currently authenticated admin.
     *
     * @return the AdminType of the currently authenticated admin
     * @throws NullPointerException if no admin is authenticated
     */
    public static String getCurrentAdminType(@NotNull AdminRepository adminRepository) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Admin> adminOptional = adminRepository.findByUserDetails(currentUser);
        if (adminOptional.isPresent()) {
            return adminOptional.get().getAdminType().toString();
        } else {
            return "";
        }
    }
}
