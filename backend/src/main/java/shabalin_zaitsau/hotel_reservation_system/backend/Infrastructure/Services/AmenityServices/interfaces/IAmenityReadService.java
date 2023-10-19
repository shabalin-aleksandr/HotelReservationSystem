package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;

import java.util.List;
import java.util.UUID;

public interface IAmenityReadService {

    List<ViewAmenityDto> findAllAmenity();
    ViewAmenityDto findAmenityById(UUID hotelI, UUID roomId,UUID amenityId);
    List<ViewAmenityDto> findAllAmenityByRoomId(UUID hotelId,UUID roomId);

}
