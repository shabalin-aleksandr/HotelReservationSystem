package shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.AmenityDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.AmenityReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.AmenityWriteService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmenityExternalService {
    private final AmenityReadService amenityReadService;
    private final AmenityWriteService amenityWriteService;
    private final AmenityDeleteService amenityDeleteService;


    public List <ViewAmenityDto> findAllAmenity(){
        return amenityReadService.findAllAmenity();
    }


    public ViewAmenityDto findAmenityById(UUID hotelId, UUID roomId, UUID amenityId){
        return amenityReadService.findAmenityById(hotelId, roomId, amenityId);
    }

    public List<ViewAmenityDto> findAllAmenityByRoomId(UUID hotelId, UUID roomId){
        return amenityReadService.findAllAmenityByRoomId(hotelId, roomId);
    }

    public ViewAmenityDto addAmenity(
            UUID hotelId,
            UUID roomId,
            IAmenityCreate amenityToCreate
    ) {
        return amenityWriteService.addAmenity(hotelId, roomId, amenityToCreate);
    }

    public ViewAmenityDto editAmenity(
            UUID hotelId,
            UUID roomId,
            UUID amenityId,
            @NotNull IAmenityUpdate amenityToUpdate
    ) {
        return amenityWriteService.editAmenity(hotelId, roomId, amenityId, amenityToUpdate);
    }

    public void removeAmenityInRoomById(UUID hotelId, UUID roomId,UUID amenityId){
        amenityDeleteService.removeAmenityInRoomById(hotelId, roomId, amenityId);
    }

    public void removeAllAmenityForRoom(UUID hotelId ,UUID roomId){
        amenityDeleteService.removeAllAmenityForRoom(hotelId, roomId);
    }


}
