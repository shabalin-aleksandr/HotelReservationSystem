package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EventLayer;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;

@Service
@RequiredArgsConstructor
public class UserEventListener {

    private final UserRepository userRepository;

    @EventListener
    public void handleUserExistCheckEvent(@NotNull UserExistCheckEvent event) {
        boolean exists = userRepository.existsById(event.getUserId());
        event.setUserExists(exists);
    }
}
