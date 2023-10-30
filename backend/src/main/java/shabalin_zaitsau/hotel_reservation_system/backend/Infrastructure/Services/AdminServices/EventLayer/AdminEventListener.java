package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EventLayer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminEventListener {

    private final AdminRepository adminRepository;

    @EventListener
    public void handleAdminExistsCheckEvent(AdminExistsCheckEvent event) {
        Optional<Admin> adminOptional = adminRepository.findById(event.getAdminId());
        if (adminOptional.isPresent()) {
            event.setAdmin(adminOptional.get());
            event.setAdminExists(true);
        } else {
            event.setAdminExists(false);
        }
    }

    @EventListener
    public void handleDeleteAdminEvent(DeleteAdminEvent event) {
        Optional<Admin> adminOpt = adminRepository.findByUserDetailsUserId(event.getUserId());
        adminOpt.ifPresent(adminRepository::delete);
        event.setAdminDeleted(true);
    }
}
