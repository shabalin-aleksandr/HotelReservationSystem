package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IRoomReadService {
    List<ViewRoomDto> findAllRooms();
    ViewRoomDto findRoomById(UUID hotelId, UUID roomId);
    List<ViewRoomDto> findAllRoomsByHotelId(UUID hotelId);
    List<ViewRoomDto> findAvailableRoomsInHotelForDateRange(UUID hotelId, Date fromDate, Date toDate);
}
