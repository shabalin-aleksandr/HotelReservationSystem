package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;

@Service
@RequiredArgsConstructor
public class HotelEventListener {

    private final HotelRepository hotelRepository;

    @EventListener
    public void handleHotelExistsCheckEvent(@NotNull HotelExistsCheckEvent event) {
        boolean exists = hotelRepository.existsById(event.getHotelId());
        event.setHotelExists(exists);
    }
}
