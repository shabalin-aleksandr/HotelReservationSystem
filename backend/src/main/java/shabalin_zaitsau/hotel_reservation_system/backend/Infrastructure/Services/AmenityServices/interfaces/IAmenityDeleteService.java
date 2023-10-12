package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces;

import java.util.UUID;

public interface IAmenityDeleteService {
    void removeAmenityById(UUID amenityId);
}
