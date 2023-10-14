package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces;

public interface IUserCreate {
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhoneNumber();
    String getCountry();
    String getRegion();
    String getCity();
}
