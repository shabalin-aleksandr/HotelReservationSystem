package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.interfaces.IUserDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeleteService implements IUserDeleteService {

    private final UserRepository userRepository;

    @Override
    public void removeUserById(UUID userId) {
        userRepository.findById(userId);
        boolean exists = userRepository.existsById(userId);

        if (!exists) {
            throw new EntityNotFoundException("User with id: "+ userId + " not found");
        }
        userRepository.deleteById(userId);
    }
}
