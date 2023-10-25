package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UserMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserPasswordUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.AuthConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.SamePasswordException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AuthExceptions.WrongPasswordException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.UserConflictException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.interfaces.IUserWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation.UserDataValidator;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.SecurityUtils;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWriteService implements IUserWriteService {

    private final UserReadService userReadService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDataValidator userValidator;
    private final UserMapper userMapper;
    private final JsonPatch jsonPatch;

    @Override
    public ViewUserDto editUser(UUID userId, IUserUpdate userToUpdate) {
        User existingUser = userReadService.fetchUserById(userId);

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        String currentUserRole = SecurityUtils.getCurrentUserRole();

        if (!currentUserId.equals(userId) && !currentUserRole.equals("ADMIN")) {
            throw new AccessDeniedException("You do not have permission to edit this user's details.");
        }

        User patchUser = userMapper.toUser(userToUpdate);
        User updatedUser = jsonPatch.mergePatch(existingUser, patchUser, User.class);

        checkConflicts(updatedUser, existingUser);

        return UserMapper.toUserResponseDto(userRepository.save(updatedUser));
    }

    @Override
    public ResponseEntity<String> updateUserPassword(UUID userId, IUserPasswordUpdate passwordToUpdate) {
        User existingUser = userReadService.fetchUserById(userId);
        validateUserPermission(userId);
        validatePasswordFields(passwordToUpdate);
        validateOldPassword(passwordToUpdate.getOldPassword(), existingUser.getPassword());
        validateNewPassword(passwordToUpdate.getOldPassword(), passwordToUpdate.getNewPassword());

        existingUser.setPassword(passwordEncoder.encode(passwordToUpdate.getNewPassword()));
        userRepository.save(existingUser);
        return ResponseEntity.ok("Password updated successfully");
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

    private void validateUserPermission(UUID userId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        if (!currentUserId.equals(userId)) {
            throw new AccessDeniedException("You do not have permission to edit this user's password.");
        }
    }

    private void validatePasswordFields(@NotNull IUserPasswordUpdate passwordToUpdate) {
        if (passwordToUpdate.getOldPassword() == null || passwordToUpdate.getNewPassword() == null) {
            throw new IllegalArgumentException("Both oldPassword and newPassword fields must be provided");
        }
    }

    private void validateOldPassword(String oldPassword, String existingPassword) {
        if (!passwordEncoder.matches(oldPassword, existingPassword)) {
            throw new WrongPasswordException("The provided current password is incorrect");
        }
    }

    private void validateNewPassword(@NotNull String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new SamePasswordException("The new password must be different from the current password");
        }
        if (userValidator.isPasswordValid(newPassword)) {
            throw new AuthConflictException("Invalid new password format");
        }
    }
}
   