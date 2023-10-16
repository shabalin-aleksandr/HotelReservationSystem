package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;

import java.util.List;
import java.util.UUID;

public interface IAdminReadService {
    List<ViewAdminDto> findAllAdmins();
    ViewAdminDto findAdminById(UUID adminId);
}
