package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;

import java.util.List;
import java.util.UUID;

public interface IUserReadService {
    List<ViewUserDto> findAllUsers();
    ViewUserDto findUserByEmail(String email);
    ViewUserDto findUserById(UUID user_id);

}
