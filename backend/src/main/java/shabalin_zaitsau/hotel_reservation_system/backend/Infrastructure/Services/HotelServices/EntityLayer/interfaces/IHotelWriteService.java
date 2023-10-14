package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelUpdate;

import java.util.UUID;

public interface IHotelWriteService {
    ViewHotelDto addHotel(IHotelCreate hotelToCreate);
    ViewHotelDto editHotel(UUID hotelId, IHotelUpdate updateHotelDto);
    double putRate(UUID placeId, double rate);
}
