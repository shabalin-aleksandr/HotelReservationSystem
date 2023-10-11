package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

   public static ViewRoomDto toRoomResponseDto(Room room) {
       Set<ShortViewReservationDto> viewReservations = room
               .getReservations()
               .stream()
               .map(reservation -> MainDtoMapper.mapReservationToViewDto(reservation, null, room))
               .collect(Collectors.toSet());

       return getViewRoomDto(room, viewReservations);
   }

   @NotNull
   private static ViewRoomDto getViewRoomDto(Room room, Set<ShortViewReservationDto> viewReservations) {
       ViewRoomDto viewRoomDto = new ViewRoomDto();
       viewRoomDto.setRoomId(room.getRoomId());
       if (room.getHotel() != null) {
           viewRoomDto.setHotelId(room.getHotel().getHotelId());
       }
       viewRoomDto.setRoomNumber(room.getRoomNumber());
       viewRoomDto.setCategory(room.getCategory());
       viewRoomDto.setPricePerNight(room.getPricePerNight());
       viewRoomDto.setReservations(viewReservations);
       return viewRoomDto;
   }

   public static Room toRoom(CreateRoomDto createRoomDto, Hotel hotel) {
       Room room = new Room();
       room.setRoomNumber(createRoomDto.getRoomNumber());
       room.setCategory(createRoomDto.getCategory());
       room.setPricePerNight(createRoomDto.getPricePerNight());
       room.setHotel(hotel);
       room.setReservations(new HashSet<>());
       return room;
   }
}
