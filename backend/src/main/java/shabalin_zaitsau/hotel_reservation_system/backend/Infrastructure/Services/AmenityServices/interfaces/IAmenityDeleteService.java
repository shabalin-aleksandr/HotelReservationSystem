package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces;

import java.util.UUID;

public interface IAmenityDeleteService {
    void removeAmenityInRoomById(UUID hotelId, UUID roomId, UUID amenityId);
    void removeAllAmenityForRoom(UUID hotelId ,UUID roomId);
}
