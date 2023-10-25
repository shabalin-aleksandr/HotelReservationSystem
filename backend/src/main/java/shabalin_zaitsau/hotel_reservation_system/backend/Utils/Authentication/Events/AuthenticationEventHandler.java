package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Events;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationEventHandler {

    private final UserRepository userRepository;

    @EventListener
    public void handleUserAuthenticationCheckEvent(@NotNull UserAuthenticationCheckEvent event) {
        Optional<User> optionalUser = userRepository.findUserByEmail(event.getEmail());
        optionalUser.ifPresent(event::setUserDetails);
    }

    @EventListener
    public void handleUserExistByEmailCheckEvent(UserExistByEmailCheckEvent event) {
        Optional<User> optionalUser = userRepository.findUserByEmail(event.getEmail());
        if (optionalUser.isPresent()) {
            event.setUserExists(true);
            event.setUserDetails(optionalUser.get());
        } else {
            event.setUserExists(false);
        }
    }
}
