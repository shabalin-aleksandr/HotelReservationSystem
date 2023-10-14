package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;

import java.util.List;
import java.util.UUID;

public interface IHotelReadService {
    List<ViewHotelDto> findAllHotels();
    ViewHotelDto findHotelById(UUID hotelId);
}
