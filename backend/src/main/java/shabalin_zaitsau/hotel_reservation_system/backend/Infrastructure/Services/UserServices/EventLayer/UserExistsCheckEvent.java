package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EventLayer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class UserExistsCheckEvent extends ApplicationEvent {

    private final UUID userId;
    private boolean userExists;

    public UserExistsCheckEvent(UUID userId) {
        super(userId);
        this.userId = userId;
    }
}
