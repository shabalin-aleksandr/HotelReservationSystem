package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.AmenityMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces.IAmenityReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer.HotelExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EventLayer.RoomExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AmenityRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityReadService implements IAmenityReadService {

    private final AmenityRepository amenityRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
     public List<ViewAmenityDto> findAllAmenity(){
         return amenityRepository
                 .findAll()
                 .stream()
                 .map(AmenityMapper::toAmenityResponseDto)
                 .collect(Collectors.toList());
     }
    @Override
    public ViewAmenityDto findAmenityById(UUID hotelId, UUID roomId, UUID amenityId){
       validateRoomExists(hotelId, roomId);
       Amenity amenity = fetchAmenityById(hotelId, roomId, amenityId);
       return  AmenityMapper.toAmenityResponseDto(amenity);
    }

    @Override
    public List<ViewAmenityDto> findAllAmenityByRoomId(UUID hotelId,UUID roomId){
        validateRoomExists(hotelId,roomId);
        return amenityRepository
                .findByRoom_RoomId(roomId)
                .stream()
                .map(AmenityMapper::toAmenityResponseDto)
                .collect(Collectors.toList());
    }


    protected void validateHotelExists(UUID hotelId) {
        HotelExistsCheckEvent event = new HotelExistsCheckEvent(hotelId);
        eventPublisher.publishEvent(event);
        if (!event.isHotelExists()) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " doesn't exist");
        }
    }


    protected void validateRoomExists(UUID hotelId, UUID roomId) {
        RoomExistsCheckEvent event = new RoomExistsCheckEvent(this, hotelId, roomId);
        eventPublisher.publishEvent(event);
        if (!event.isRoomExists()) {
            throw new EntityNotFoundException("Room with id: " + roomId + " doesn't exist");
        }
    }

    protected Amenity fetchAmenityById(UUID hotelId, UUID roomId, UUID amenityId) {
        validateHotelExists(hotelId);
        validateRoomExists(hotelId, roomId);
        return amenityRepository
                .findByRoom_Hotel_HotelIdAndRoom_RoomIdAndAmenityId(
                        hotelId, roomId, amenityId
                )
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Amenity %s for room %s doesn't exist in hotel with id: %s",
                            amenityId,
                            roomId,
                            hotelId
                    );
                    return new EntityNotFoundException(errorMessage);
                });

    }









 }
