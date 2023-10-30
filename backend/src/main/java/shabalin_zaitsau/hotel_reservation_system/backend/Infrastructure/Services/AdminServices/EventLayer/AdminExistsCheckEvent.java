package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EventLayer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;

import java.util.UUID;

@Getter
@Setter
public class AdminExistsCheckEvent extends ApplicationEvent {

    private final UUID adminId;
    private boolean adminExists;
    private Admin admin;

    public AdminExistsCheckEvent(UUID adminId) {
        super(adminId);
        this.adminId = adminId;
    }
}



