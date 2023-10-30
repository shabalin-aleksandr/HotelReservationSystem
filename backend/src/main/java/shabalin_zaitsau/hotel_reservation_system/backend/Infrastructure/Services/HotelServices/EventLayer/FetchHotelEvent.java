package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;

import java.util.UUID;

@Getter
@Setter
public class FetchHotelEvent extends ApplicationEvent {
    private final UUID hotelId;
    private Hotel hotel;

    public FetchHotelEvent(UUID hotelId) {
        super(hotelId);
        this.hotelId = hotelId;
    }
}
