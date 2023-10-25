package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;

public interface IAdminRegistrationRequest extends IRegistrationRequest{
    AdminType getAdminType();
}
