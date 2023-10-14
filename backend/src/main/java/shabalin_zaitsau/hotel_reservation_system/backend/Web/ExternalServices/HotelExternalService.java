package shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.HotelDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.HotelReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.HotelWriteService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelExternalService {

    private final HotelReadService hotelReadService;
    private final HotelWriteService hotelWriteService;
    private final HotelDeleteService hotelDeleteService;

    public List<ViewHotelDto> findAllHotels() {
        return hotelReadService.findAllHotels();
    }

    public ViewHotelDto findHotelById(UUID hotelId) {
        return hotelReadService.findHotelById(hotelId);
    }

    public ViewHotelDto addHotel(IHotelCreate hotelToCreate) {
        return hotelWriteService.addHotel(hotelToCreate);
    }

    public ViewHotelDto editHotel(UUID hotelId, IHotelUpdate hotelToUpdate) {
        return hotelWriteService.editHotel(hotelId, hotelToUpdate);
    }

    public double putRate(UUID hotelId, double rate) {
        return hotelWriteService.putRate(hotelId, rate);
    }

    public void removeHotelById(UUID hotelId) {
        hotelDeleteService.removeHotelById(hotelId);
    }
}
