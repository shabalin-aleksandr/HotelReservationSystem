package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomUpdate;

import java.util.UUID;

public interface IRoomWriteService {

    ViewRoomDto addRoom(UUID hotelId, IRoomCreate roomToCreate);

    ViewRoomDto editRoom(UUID hotelId, UUID roomId, IRoomUpdate roomToUpdate);


}
