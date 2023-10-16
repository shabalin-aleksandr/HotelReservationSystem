package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.interfaces;

import java.util.UUID;

public interface IAdminDeleteService {
    void removeAdminById(UUID adminId);
}
