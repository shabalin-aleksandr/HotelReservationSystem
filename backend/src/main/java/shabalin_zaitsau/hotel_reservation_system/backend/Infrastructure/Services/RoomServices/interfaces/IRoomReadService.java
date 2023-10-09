package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;

import java.util.List;
import java.util.UUID;

public interface IRoomReadService {
    List<ViewRoomDto> findAllRooms();
    ViewRoomDto findRoomById(UUID hotelId, UUID roomId);
    List<ViewRoomDto> findAllRoomsByHotelId(UUID hotelId);
}
