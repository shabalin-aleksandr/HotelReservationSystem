package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces;

public interface IRegistrationRequest {
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPassword();
    String getPhoneNumber();
    String getCountry();
    String getRegion();
    String getCity();
}
