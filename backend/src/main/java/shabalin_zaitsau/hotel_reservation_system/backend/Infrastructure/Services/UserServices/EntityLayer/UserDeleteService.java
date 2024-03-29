package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EventLayer.DeleteAdminEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.interfaces.IUserDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.SecurityUtils;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeleteService implements IUserDeleteService {

    private final UserRepository userRepository;
    private final UserReadService userReadService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void removeUserById(UUID userId) {
        userReadService.fetchUserById(userId);
        deleteAssociatedAdmin(userId);
        userRepository.deleteById(userId);
    }

    public ResponseEntity<String> deleteOwnAccount() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        userReadService.fetchUserById(currentUserId);
        boolean adminDeleted = deleteAssociatedAdmin(currentUserId);

        if (adminDeleted) {
            userRepository.deleteById(currentUserId);
            return ResponseEntity
                    .ok("Your account has been successfully deleted");
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the account");
        }
    }

    protected boolean deleteAssociatedAdmin(UUID userId) {
        DeleteAdminEvent deleteAdminEvent = new DeleteAdminEvent(userId);
        eventPublisher.publishEvent(deleteAdminEvent);
        return deleteAdminEvent.isAdminDeleted();
    }
}
