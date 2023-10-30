package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EntityLayer.interfaces.IAdminDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminDeleteService implements IAdminDeleteService {

    private final AdminRepository adminRepository;
    private final AdminReadService adminReadService;

    @Override
    public void removeAdminById(UUID adminId) {
        adminReadService.fetchAdminById(adminId);
        adminRepository.deleteById(adminId);
    }
}
