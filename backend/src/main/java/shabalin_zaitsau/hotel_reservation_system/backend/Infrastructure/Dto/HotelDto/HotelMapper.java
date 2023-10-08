package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HotelMapper {

    public static ViewHotelDto toHotelResponseDto(Hotel hotel) {
        Set<ShortViewRoomDto> viewAvailableRooms = hotel
                .getAvailableRooms()
                .stream()
                .map(rooms -> {
                    ShortViewRoomDto viewRoomDto = new ShortViewRoomDto();
                    viewRoomDto.setRoomId(rooms.getRoomId());
                    viewRoomDto.setHotelId(rooms.getHotel().getHotelId());
                    viewRoomDto.setRoomNumber(rooms.getRoomNumber());
                    viewRoomDto.setCategory(rooms.getCategory());
                    viewRoomDto.setPricePerNight(rooms.getPricePerNight());
                    return viewRoomDto;
                })
                .collect(Collectors.toSet());

        ViewHotelDto viewHotelDto = new ViewHotelDto();
        viewHotelDto.setHotelId(hotel.getHotelId());
        viewHotelDto.setHotelName(hotel.getHotelName());
        viewHotelDto.setCountry(hotel.getCountry());
        viewHotelDto.setCity(hotel.getCity());
        viewHotelDto.setAddress(hotel.getAddress());
        viewHotelDto.setReceptionNumber(hotel.getReceptionNumber());
        viewHotelDto.setRating(hotel.getRating());
        viewHotelDto.setAvailableRooms(viewAvailableRooms);
        return viewHotelDto;
    }

    public static Hotel toHotel(CreateHotelDto createHotelDto) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(createHotelDto.getHotelName());
        hotel.setCountry(createHotelDto.getCountry());
        hotel.setCity(createHotelDto.getCity());
        hotel.setAddress(createHotelDto.getAddress());
        hotel.setReceptionNumber(createHotelDto.getReceptionNumber());
        return hotel;
    }
}
