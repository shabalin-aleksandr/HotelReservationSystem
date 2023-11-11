package shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.RoomDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.RoomReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.RoomWriteService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomExternalService {

    private final RoomReadService roomReadService;
    private final RoomWriteService roomWriteService;
    private final RoomDeleteService roomDeleteService;

    public List<ViewRoomDto> findAllRooms() {
        return roomReadService.findAllRooms();
    }

    public List<ViewRoomDto> findAllRoomsByHotelId(UUID hotelId) {
        return roomReadService.findAllRoomsByHotelId(hotelId);
    }

    public ViewRoomDto findRoomById(UUID hotelId, UUID roomId) {
        return roomReadService.findRoomById(hotelId, roomId);
    }
    public List<ViewRoomDto> findAvailableRoomsInHotelForDateRange(UUID hotelId, Date fromDate, Date toDate){
        return roomReadService.findAvailableRoomsInHotelForDateRange(hotelId, fromDate, toDate);
    }
    public ViewRoomDto addRoom(UUID hotelId, IRoomCreate roomToCreate) {
        return roomWriteService.addRoom(hotelId, roomToCreate);
    }

    public ViewRoomDto editRoom(UUID hotelId, UUID roomId, IRoomUpdate roomToUpdate) {
        return roomWriteService.editRoom(hotelId, roomId, roomToUpdate);
    }

    public void removeRoomById(UUID hotelId, UUID roomId) {
        roomDeleteService.removeRoomById(hotelId, roomId);
    }


}
