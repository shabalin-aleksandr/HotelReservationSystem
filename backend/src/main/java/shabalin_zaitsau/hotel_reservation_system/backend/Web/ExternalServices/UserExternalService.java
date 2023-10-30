package shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserPasswordUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.UserDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.UserReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.UserWriteService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserExternalService {

    private final UserReadService userReadService;
    private final UserWriteService userWriteService;
    private final UserDeleteService userDeleteService;

    public List<ViewUserDto> findAllUsers() {
        return userReadService.findAllUsers();
    }

    public ViewUserDto findUserById(UUID userId) {
        return userReadService.findUserById(userId);
    }

    public ViewUserDto editUser(UUID userId, IUserUpdate userToUpdate) {
        return userWriteService.editUser(userId, userToUpdate);
    }

    public ResponseEntity<String> updateUserPassword(UUID userId, IUserPasswordUpdate passwordToUpdate) {
        return userWriteService.updateUserPassword(userId, passwordToUpdate);
    }

    public void removeUserById(UUID userId) {
        userDeleteService.removeUserById(userId);
    }

    public ResponseEntity<String> deleteAccount() {
        return userDeleteService.deleteOwnAccount();
    }
}
