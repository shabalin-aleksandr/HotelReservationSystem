package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UserMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.UserConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.interfaces.IUserWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWriteService implements IUserWriteService {

    private final UserReadService userReadService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JsonPatch jsonPatch;

    @Override
    public ViewUserDto addUser(@NotNull IUserCreate userToCreate) {
        if (userToCreate.getEmail() == null || userToCreate.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user = userMapper.toUser(userToCreate);
        return UserMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public ViewUserDto editUser(UUID userId, IUserUpdate userToUpdate) {
        User existingUser = userReadService.fetchUserById(userId);

        User patchUser = userMapper.toUser(userToUpdate);
        User updatedUser = jsonPatch.mergePatch(existingUser, patchUser, User.class);

        checkConflicts(updatedUser, existingUser);

        return UserMapper.toUserResponseDto(userRepository.save(updatedUser));
    }

    private void checkConflicts(@NotNull User updatedUser, User existingUser) {
        if (updatedUser.getEmail() != null) {
            String newEmail = updatedUser.getEmail();
            if (!existingUser.getEmail().equals(newEmail)) {
                User userWithSameEmail = null;
                try {
                    userWithSameEmail = userReadService.fetchUserByEmail(newEmail);
                } catch (EntityNotFoundException e) {
                    throw new EntityNotFoundException("User with email: " + userWithSameEmail + " not found");
                }
                if (userWithSameEmail != null) {
                    throw new UserConflictException("User with Email: " + newEmail + " already exists");
                }
            }
        }
    }
}
   