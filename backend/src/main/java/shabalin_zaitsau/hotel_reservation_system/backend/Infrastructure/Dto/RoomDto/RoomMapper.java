package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto;

import jakarta.persistence.EntityManager;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomCreate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class RoomMapper {

    private final EntityManager entityManager;

    @NotNull
    public static ViewRoomDto toRoomResponseDto(@NotNull Room room) {
       Set<ShortViewReservationDto> viewReservations = mapReservationToViewDto(room.getReservations());
       Set<ViewAmenityDto> viewAmenities = mapAmenityToViewDto(room.getAvailableAmenities());
       return  toRoomResponseDto(room, viewReservations, viewAmenities);
   }

    @NotNull
    public static ViewRoomDto toRoomResponseDto(
            @NotNull Room room,
            Set<ShortViewReservationDto> viewReservation,
            Set<ViewAmenityDto> viewAmenity
    ) {
        return getViewRoomDto(
                room,
                viewReservation != null ? viewReservation : mapReservationToViewDto(room.getReservations()),
                viewAmenity != null ? viewAmenity : mapAmenityToViewDto(room.getAvailableAmenities())
        );
    }

   @NotNull
   public static Set<ShortViewReservationDto> mapReservationToViewDto(@NotNull Set<Reservation> reservations) {
        return reservations
                .stream()
                .map(MainDtoMapper::mapReservationToViewDto)
                .collect(Collectors.toSet());
   }

   @NotNull
   public static Set<ViewAmenityDto> mapAmenityToViewDto(@NotNull Set<Amenity> amenities) {
        return amenities
                .stream()
                .map(MainDtoMapper::mapAmenityToViewDto)
                .collect(Collectors.toSet());
   }
   @NotNull
   private static ViewRoomDto getViewRoomDto(
           @NotNull Room room,
           Set<ShortViewReservationDto> viewReservations,
           Set<ViewAmenityDto> viewAmenities
   ) {
       ViewRoomDto viewRoomDto = new ViewRoomDto();
       viewRoomDto.setRoomId(room.getRoomId());
       if(room.getHotel() != null) {
           viewRoomDto.setHotelId(room.getHotel().getHotelId());
       }
       viewRoomDto.setRoomNumber(room.getRoomNumber());
       viewRoomDto.setCategory(room.getCategory());
       viewRoomDto.setPricePerNight(room.getPricePerNight());
       viewRoomDto.setReservations(viewReservations);
       viewRoomDto.setAmenities(viewAmenities);
       return viewRoomDto;
   }

    @NotNull
    public Room toRoom(@NotNull IRoomCreate roomToCreate, UUID hotelId) {
        Room room = new Room();
        room.setRoomNumber(roomToCreate.getRoomNumber());
        room.setCategory(roomToCreate.getCategory());
        room.setPricePerNight(roomToCreate.getPricePerNight());
        Hotel hotelReference = entityManager.getReference(Hotel.class, hotelId);
        room.setHotel(hotelReference);
        room.setReservations(new HashSet<>());
        return room;
    }
}
