package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;

import java.util.Set;
import java.util.stream.Collectors;

import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper.toHotelResponseDto;

@Component
public class RoomMapper {

   public static ViewRoomDto toRoomResponseDto(Room room) {
       Set<ViewReservationDto> viewReservations = room
               .getReservations()
               .stream()
               .map(reservation -> {
                   ViewReservationDto viewReservationDto = new ViewReservationDto();
                   viewReservationDto.setReservationId(reservation.getReservationId());
                   viewReservationDto.setUserId(reservation.getUser().getUserId());
                   viewReservationDto.setRoomId(reservation.getRoom().getRoomId());
                   viewReservationDto.setReservationFrom(reservation.getReservationFrom());
                   viewReservationDto.setReservationTo(reservation.getReservationTo());
                   viewReservationDto.setTotalPrice(reservation.getTotalPrice());
                   return viewReservationDto;
               })
               .collect(Collectors.toSet());

       ViewRoomDto viewRoomDto = new ViewRoomDto();
       viewRoomDto.setRoomId(room.getRoomId());
       viewRoomDto.setHotelId(room.getHotel().getHotelId());
       viewRoomDto.setRoomNumber(room.getRoomNumber());
       viewRoomDto.setCategory(room.getCategory());
       viewRoomDto.setPricePerNight(room.getPricePerNight());
       viewRoomDto.setReservations(viewReservations);
       return viewRoomDto;
   }

   // TODO: Reservation
   public static Room toRoom(CreateRoomDto createRoomDto, Hotel hotel) {
       Room room = new Room();
       room.setRoomNumber(createRoomDto.getRoomNumber());
       room.setCategory(createRoomDto.getCategory());
       room.setPricePerNight(createRoomDto.getPricePerNight());
       room.setHotel(hotel);
       return room;
   }
}
