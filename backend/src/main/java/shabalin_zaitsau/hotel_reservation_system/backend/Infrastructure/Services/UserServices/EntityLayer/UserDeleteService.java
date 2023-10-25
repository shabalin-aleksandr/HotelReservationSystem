package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    @Override
    public void removeUserById(UUID userId) {
        userReadService.fetchUserById(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public ResponseEntity<String> deleteAccount() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        userReadService.fetchUserById(currentUserId);
        userRepository.deleteById(currentUserId);
        return ResponseEntity.ok("Your account has been successfully deleted");
    }
}
