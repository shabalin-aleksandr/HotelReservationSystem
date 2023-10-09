package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HotelMapper {

    public static ViewHotelDto toHotelResponseDto(Hotel hotel) {
        Set<ShortViewRoomDto> viewAvailableRooms = mapRoomsToViewDto(hotel.getAvailableRooms());
        return toHotelResponseDto(hotel, viewAvailableRooms);
    }

    public static ViewHotelDto toHotelResponseDto(Hotel hotel, Set<ShortViewRoomDto> viewAvailableRooms) {
        return getViewHotelDto
                (
                        hotel, viewAvailableRooms != null ? viewAvailableRooms : mapRoomsToViewDto
                                (hotel.getAvailableRooms())
                );
    }

    public static Set<ShortViewRoomDto> mapRoomsToViewDto(Set<Room> rooms) {
        return rooms.stream()
                .map(MainDtoMapper::mapRoomToViewDto)
                .collect(Collectors.toSet());
    }

    @NotNull
    private static ViewHotelDto getViewHotelDto(Hotel hotel, Set<ShortViewRoomDto> shortViewRoom) {
        ViewHotelDto viewHotelDto = new ViewHotelDto();
        viewHotelDto.setHotelId(hotel.getHotelId());
        viewHotelDto.setHotelName(hotel.getHotelName());
        viewHotelDto.setCountry(hotel.getCountry());
        viewHotelDto.setCity(hotel.getCity());
        viewHotelDto.setAddress(hotel.getAddress());
        viewHotelDto.setReceptionNumber(hotel.getReceptionNumber());
        viewHotelDto.setRating(hotel.getRating());
        viewHotelDto.setAvailableRooms(shortViewRoom);
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
