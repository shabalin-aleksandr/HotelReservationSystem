package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto;


import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityCreate;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class AmenityMapper {

    // ochen horosho posmotri

    private final EntityManager entityManager;
    @NotNull
    private static ViewAmenityDto getViewAmenityDto(
            @NotNull Amenity amenity
    ) {
        ViewAmenityDto viewAmenityDto = new ViewAmenityDto();
        viewAmenityDto.setAmenityId(amenity.getAmenityId());
        if (amenity.getRoom() != null){
            viewAmenityDto.setRoomId(amenity.getRoom().getRoomId());
        }
        viewAmenityDto.setAmenityName(amenity.getAmenityName());
        viewAmenityDto.setDescription(amenity.getDescription());
        viewAmenityDto.setRoomId(amenity.getRoom().getRoomId());
        return viewAmenityDto;
    }

    public Amenity toAmenity(@NotNull  IAmenityCreate createAmenityDto, UUID roomId) {
        Amenity amenity = new Amenity();
        amenity.setAmenityName(createAmenityDto.getAmenityName());
        amenity.setDescription(createAmenityDto.getDescription());
        Room roomReference = entityManager.getReference(Room.class, roomId);
        amenity.setRoom(roomReference);
        return amenity;
    }
// hz pravilno?
    public static ViewAmenityDto toAmenityResponseDto(@NotNull Amenity amenity) {
        return getViewAmenityDto(amenity);
    }
}
