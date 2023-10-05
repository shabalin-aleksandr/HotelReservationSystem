package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;

@Component
public class HotelMapper {

    public static ViewHotelDto toHotelResponseDto(Hotel hotel) {
        return new ViewHotelDto(
                hotel.getHotelId(),
                hotel.getHotelName(),
                hotel.getCountry(),
                hotel.getCity(),
                hotel.getAddress(),
                hotel.getReceptionNumber(),
                hotel.getRating()
        );
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
