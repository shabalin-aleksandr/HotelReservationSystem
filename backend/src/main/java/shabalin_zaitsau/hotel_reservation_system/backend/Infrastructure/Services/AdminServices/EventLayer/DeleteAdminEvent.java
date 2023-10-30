package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EventLayer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class DeleteAdminEvent extends ApplicationEvent {
    private final UUID userId;
    private boolean adminDeleted = false;

    public DeleteAdminEvent(UUID userId) {
        super(userId);
        this.userId = userId;
    }
}
