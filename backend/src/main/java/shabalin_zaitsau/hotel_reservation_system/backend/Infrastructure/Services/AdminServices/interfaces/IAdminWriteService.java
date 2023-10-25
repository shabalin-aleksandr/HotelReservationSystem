package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminUpdate;

import java.util.UUID;

public interface IAdminWriteService {
    ViewAdminDto editAdmin(UUID userId, UUID adminId, IAdminUpdate adminToUpdate);
}
