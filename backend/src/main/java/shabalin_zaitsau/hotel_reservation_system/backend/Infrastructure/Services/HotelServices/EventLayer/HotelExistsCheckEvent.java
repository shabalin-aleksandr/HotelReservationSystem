package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class HotelExistsCheckEvent extends ApplicationEvent {

    private final UUID hotelId;
    private boolean hotelExists;

    public HotelExistsCheckEvent(UUID hotelId) {
        super(hotelId);
        this.hotelId = hotelId;
    }
}

