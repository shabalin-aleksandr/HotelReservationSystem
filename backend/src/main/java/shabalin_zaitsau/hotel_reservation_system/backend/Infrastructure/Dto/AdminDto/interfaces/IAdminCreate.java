package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;

public interface IAdminCreate {
    AdminType getAdminType();
}
