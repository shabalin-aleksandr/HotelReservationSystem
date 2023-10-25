package shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.AdminDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.AdminReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.AdminWriteService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminExternalService {

    private final AdminReadService adminReadService;
    private final AdminWriteService adminWriteService;
    private final AdminDeleteService adminDeleteService;

    public List<ViewAdminDto> findAllAdmins() {
        return adminReadService.findAllAdmins();
    }

    public ViewAdminDto findAdminById(UUID adminId) {
        return adminReadService.findAdminById(adminId);
    }

    public ViewAdminDto editAdmin(UUID userId, UUID adminId, IAdminUpdate adminToUpdate) {
        return adminWriteService.editAdmin(userId, adminId, adminToUpdate);
    }

    public void removeAdminById(UUID adminId) {
        adminDeleteService.removeAdminById(adminId);
    }
}
