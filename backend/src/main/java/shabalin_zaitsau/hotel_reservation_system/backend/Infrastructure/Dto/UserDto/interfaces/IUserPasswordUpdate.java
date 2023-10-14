package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces;

public interface IUserPasswordUpdate {
    String getOldPassword();
    String getNewPassword();
}
