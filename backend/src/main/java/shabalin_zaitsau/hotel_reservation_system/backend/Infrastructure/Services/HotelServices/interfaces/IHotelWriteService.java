package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.CreateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.UpdateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;

import java.util.UUID;

public interface IHotelWriteService {
    ViewHotelDto addHotel(CreateHotelDto createHotelDto);
    ViewHotelDto editHotel(UUID hotelId, UpdateHotelDto updateHotelDto);
    double putRate(UUID placeId, double rate);
}
