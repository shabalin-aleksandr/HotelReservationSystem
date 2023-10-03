package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import org.springframework.data.jpa.repository.JpaRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;

import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {}
