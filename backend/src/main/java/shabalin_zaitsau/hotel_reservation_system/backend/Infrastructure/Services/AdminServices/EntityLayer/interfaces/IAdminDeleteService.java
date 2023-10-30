package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EntityLayer.interfaces;

import java.util.UUID;

public interface IAdminDeleteService {
    void removeAdminById(UUID adminId);
}
