package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces.IHotelDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelDeleteService implements IHotelDeleteService {

    private final HotelRepository hotelRepository;
    private final HotelReadService hotelReadService;

    @Override
    public void removeHotelById(UUID hotelId) {
        hotelReadService.fetchHotelById(hotelId);
        hotelRepository.deleteById(hotelId);
    }
}
