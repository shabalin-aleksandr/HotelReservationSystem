package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.interfaces;

import java.util.UUID;

public interface IRoomDeleteService {
    void removeRoomById(UUID hotelId, UUID roomId);
}
