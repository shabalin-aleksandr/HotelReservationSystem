package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.interfaces;

import java.util.UUID;

public interface IUserDeleteService {
    void removeUserById(UUID userId);
}
