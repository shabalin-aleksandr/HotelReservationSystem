package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces.IAmenityDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AmenityRepository;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityDeleteService implements IAmenityDeleteService {

    private final AmenityRepository amenityRepository;
    private final AmenityReadService amenityReadService;

    @Override
   public void removeAmenityInRoomById(UUID hotelId, UUID roomId, UUID amenityId){
        amenityReadService.fetchAmenityById(hotelId, roomId, amenityId);
    }

    @Override
    public void removeAllAmenityForRoom(UUID hotelId ,UUID roomId){
        amenityReadService.validateHotelExists(hotelId);
        amenityReadService.validateRoomExists(hotelId, roomId);
        amenityRepository.deleteAllByRoom_RoomId(roomId);
    }



}
