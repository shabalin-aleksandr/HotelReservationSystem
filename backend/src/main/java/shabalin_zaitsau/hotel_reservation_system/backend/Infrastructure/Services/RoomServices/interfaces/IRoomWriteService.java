package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.CreateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.UpdateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;

import java.util.UUID;

public interface IRoomWriteService {

    ViewRoomDto addRoom(UUID hotelId, CreateRoomDto createRoomDto);

    ViewRoomDto editRoom(UUID hotelId, UUID roomId, UpdateRoomDto updateRoomDto);


}
