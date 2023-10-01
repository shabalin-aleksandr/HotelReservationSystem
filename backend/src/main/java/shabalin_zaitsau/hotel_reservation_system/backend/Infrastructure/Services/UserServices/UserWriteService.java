package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.CreateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UpdateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UserMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.UserConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.interfaces.IUserWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWriteService implements IUserWriteService {

    private final UserRepository userRepository;
    private final JsonPatch jsonPatch;

    @Override
    public ViewUserDto addUser(CreateUserDto createUserDto) {
        if (createUserDto.getEmail() == null || createUserDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user = UserMapper.toUser(createUserDto);
        return UserMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public ViewUserDto editUser(UUID user_id, UpdateUserDto updateUserDto) {
        User existingUser = userRepository
                .findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: "+ user_id + " not found"));

        User patchUser = UserMapper.toUser(updateUserDto);
        User updatedUser = jsonPatch.mergePatch(existingUser, patchUser, User.class);

        checkConflicts(updatedUser, existingUser);

        return UserMapper.toUserResponseDto(userRepository.save(updatedUser));
    }

    private void checkConflicts(User updatedUser, User existingUser) {
        if (updatedUser.getEmail() != null) {
            String newEmail = updatedUser.getEmail();
            if (!existingUser.getEmail().equals(newEmail)) {
                Optional<User> userWithSameEmail = userRepository.findUserByEmail(newEmail);
                if (userWithSameEmail.isPresent()) {
                    throw new UserConflictException("User with Email: " + newEmail + " already exists");
                }
            }
        }
    }
}
