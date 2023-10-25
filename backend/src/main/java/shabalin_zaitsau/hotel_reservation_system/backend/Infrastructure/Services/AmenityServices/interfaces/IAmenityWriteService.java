package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityUpdate;

import java.util.UUID;

public interface IAmenityWriteService {
   ViewAmenityDto addAmenity(UUID hotelId ,UUID roomId, IAmenityCreate amenityToCreate);
   ViewAmenityDto editAmenity(UUID hotelId, UUID roomId, UUID AmenityId, IAmenityUpdate amenityToUpdate);

}
