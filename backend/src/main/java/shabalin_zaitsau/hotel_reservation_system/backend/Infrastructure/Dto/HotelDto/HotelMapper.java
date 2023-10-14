package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HotelMapper {

    @NotNull
    public static ViewHotelDto toHotelResponseDto(@NotNull Hotel hotel) {
        Set<ShortViewRoomDto> viewAvailableRooms = mapRoomsToViewDto(hotel.getAvailableRooms());
        return toHotelResponseDto(hotel, viewAvailableRooms);
    }

    @NotNull
    public static ViewHotelDto toHotelResponseDto(Hotel hotel, Set<ShortViewRoomDto> viewAvailableRooms) {
        return getViewHotelDto
                (
                        hotel, viewAvailableRooms != null ? viewAvailableRooms : mapRoomsToViewDto(
                                hotel.getAvailableRooms()
                        )
                );
    }

    @NotNull
    public static Set<ShortViewRoomDto> mapRoomsToViewDto(@NotNull Set<Room> rooms) {
        return rooms
                .stream()
                .map(MainDtoMapper::mapRoomToViewDto)
                .collect(Collectors.toSet());
    }

    @NotNull
    private static ViewHotelDto getViewHotelDto(@NotNull Hotel hotel, Set<ShortViewRoomDto> shortViewRoom) {
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

    @NotNull
    public Hotel toHotel(@NotNull IHotelCreate hotelToCreate) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelToCreate.getHotelName());
        hotel.setCountry(hotelToCreate.getCountry());
        hotel.setCity(hotelToCreate.getCity());
        hotel.setAddress(hotelToCreate.getAddress());
        hotel.setReceptionNumber(hotelToCreate.getReceptionNumber());
        return hotel;
    }
}
