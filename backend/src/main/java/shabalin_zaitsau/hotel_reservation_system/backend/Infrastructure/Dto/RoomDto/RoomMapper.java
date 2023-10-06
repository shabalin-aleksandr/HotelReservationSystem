package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
@Component
public class RoomMapper {

   public static ViewRoomDto toRoomResponseDto(Room room){
       return new ViewRoomDto(
               room.getRoomId(),
               room.getRoomNumber(),
               room.getCategory(),
               room.getPricePerNight(),
               room.getHotel().getHotelId()
       );
   }
   //TODO Reservation
   public static Room toRoom(CreateRoomDto createRoomDto, Hotel hotel){
       Room room = new Room();
       room.setRoomNumber(createRoomDto.getRoomNumber());
       // Category type?
       room.setCategory(createRoomDto.getCategory());
       room.setPricePerNight(Double.parseDouble(createRoomDto.getRoomNumber()));
       room.setHotel(hotel);
       return room;
   }
}
