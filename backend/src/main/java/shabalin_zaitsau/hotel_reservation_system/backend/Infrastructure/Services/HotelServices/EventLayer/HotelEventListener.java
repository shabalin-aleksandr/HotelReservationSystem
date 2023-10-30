package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
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

    @EventListener
    public void handleFetchHotelEvent(FetchHotelEvent event) {
        Hotel hotel = hotelRepository.findById(event.getHotelId())
                .orElseThrow(() -> new EntityNotFoundException(
                                        "Hotel with id: " + event.getHotelId() + " doesn't exist")
                );
        event.setHotel(hotel);
    }
}
