package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.HotelReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.RoomReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.SecurityUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionValidator {

    private final AdminRepository adminRepository;
    private final RoomReadService roomReadService;
    private final HotelReadService hotelReadService;

    public void validateAdminPermission(UUID entityAdminId) {
        UUID currentAdminId = SecurityUtils.getCurrentAdminId(adminRepository);
        String currentUserRole = SecurityUtils.getCurrentUserRole();
        String currentAdminType = SecurityUtils.getCurrentAdminType(adminRepository);
        System.out.println(currentAdminId);

        if ("ADMIN".equals(currentUserRole) && "SUPER_ADMIN".equals(currentAdminType)) {
            return;
        }
        if (currentAdminId.equals(entityAdminId)) {
            return;
        }
        throw new AccessDeniedException("You do not have permission for this action.");
    }


    public void validateUserAccess(UUID targetUserId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        String currentUserRole = SecurityUtils.getCurrentUserRole();
        String currentAdminType = SecurityUtils.getCurrentAdminType(adminRepository);

        if (currentUserId.equals(targetUserId)) {
            return;
        }
        if ("ADMIN".equals(currentUserRole) && "SUPER_ADMIN".equals(currentAdminType)) {
            return;
        }
        throw new AccessDeniedException("You do not have permission to edit this user's details.");
    }

    public void validateHotelManagementPermission(UUID hotelId) {
        UUID currentAdminId = SecurityUtils.getCurrentAdminId(adminRepository);
        Admin currentAdmin = roomReadService.validateAdminExists(currentAdminId);
        Hotel hotel = roomReadService.fetchHotelById(hotelId);

        if (currentAdmin.getAdminType() == AdminType.SUPER_ADMIN) {
            return;
        }
        if (currentAdmin.getAdminType() == AdminType.HOTEL_MANAGER) {
            if (hotel.getManagedBy().getAdminId() == null ||
                    !hotel.getManagedBy().getAdminId().equals(currentAdmin.getAdminId())) {
                throw new AccessDeniedException("HOTEL_MANAGER can't manage a hotel that they didn't create.");
            }
        } else {
            throw new AccessDeniedException("You do not have permission to manage this hotel.");
        }
    }

    public void validateReservationPermission(UUID hotelId, UUID reservationOwnerId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UUID currentAdminId = SecurityUtils.getCurrentAdminId(adminRepository);
        String currentUserRole = SecurityUtils.getCurrentUserRole();
        String currentAdminType = SecurityUtils.getCurrentAdminType(adminRepository);

        if ("ADMIN".equals(currentUserRole) && "SUPER_ADMIN".equals(currentAdminType)) {
            return;
        }
        if (currentUserId.equals(reservationOwnerId)) {
            return;
        }
        if ("ADMIN".equals(currentUserRole) && "HOTEL_MANAGER".equals(currentAdminType)) {
            Hotel hotel = hotelReadService.fetchHotelById(hotelId);
            if (hotel.getManagedBy().getAdminId().equals(currentAdminId)) {
                return;
            }
        }
        throw new AccessDeniedException("You do not have permission to edit this reservation.");
    }
}
