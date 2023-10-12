package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto;


import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;

@Component
public class AmenityMapper {

    // ochen horosho posmotri
    @NotNull
    private static ViewAmenityDto getViewAmenityDto(Amenity amenity, Room room) {
        ViewAmenityDto viewAmenityDto = new ViewAmenityDto();
        viewAmenityDto.setAmenityId(amenity.getAmenityId());
        viewAmenityDto.setAmenityName(amenity.getAmenityName());
        viewAmenityDto.setDescription(amenity.getDescription());
        viewAmenityDto.setRoomId(amenity.getRoom().getRoomId());
        return viewAmenityDto;
    }

    public static Amenity toAmenity(CreateAmenityDto createAmenityDto, Room room) {
        Amenity amenity = new Amenity();
        amenity.setAmenityName(createAmenityDto.getAmenityName());
        amenity.setDescription(createAmenityDto.getDescription());
        amenity.setRoom(room);
        return amenity;
    }
// hz pravilno?
    public static ViewAmenityDto toAmenityResponseDto(Amenity amenity) {
        ViewAmenityDto viewAmenityDto = new ViewAmenityDto();
        viewAmenityDto.setAmenityId(amenity.getAmenityId());
        viewAmenityDto.setAmenityName(amenity.getAmenityName());
        viewAmenityDto.setDescription(amenity.getDescription());

        if (amenity.getRoom() != null) {
            viewAmenityDto.setRoomId(amenity.getRoom().getRoomId());
        }
        return viewAmenityDto;
    }
}
