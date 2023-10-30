package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EntityLayer.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IUserDeleteService {
    void removeUserById(UUID userId);
    ResponseEntity<String> deleteOwnAccount();
}
