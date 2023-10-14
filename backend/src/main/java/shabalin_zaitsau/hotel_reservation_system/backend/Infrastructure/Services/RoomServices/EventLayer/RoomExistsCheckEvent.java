package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EventLayer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class RoomExistsCheckEvent extends ApplicationEvent {

    private final UUID hotelId;
    private final UUID roomId;
    private boolean roomExists;

    public RoomExistsCheckEvent(Object source, UUID hotelId, UUID roomId) {
        super(source);
        this.roomId = roomId;
        this.hotelId = hotelId;
    }
}

