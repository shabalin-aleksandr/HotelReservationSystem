package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
}
