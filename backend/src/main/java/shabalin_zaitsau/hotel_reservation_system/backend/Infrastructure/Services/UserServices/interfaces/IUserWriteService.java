package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.CreateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UpdateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;

import java.util.UUID;

public interface IUserWriteService {
    ViewUserDto addUser(CreateUserDto createUserDto);
    ViewUserDto editUser(UUID user_id, UpdateUserDto updateUserDto);
}
