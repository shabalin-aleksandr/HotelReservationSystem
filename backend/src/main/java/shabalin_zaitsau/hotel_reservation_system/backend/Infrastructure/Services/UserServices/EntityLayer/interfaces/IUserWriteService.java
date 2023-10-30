package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserPasswordUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserUpdate;

import java.util.UUID;

public interface IUserWriteService {
    ViewUserDto editUser(UUID userId, IUserUpdate userToUpdate);
    ResponseEntity<String> updateUserPassword(UUID userId, IUserPasswordUpdate passwordToUpdate);
    ViewUserDto updateUserAvatar(UUID userId, MultipartFile avatar);
    ViewUserDto removeUserAvatar(UUID userId);
}
