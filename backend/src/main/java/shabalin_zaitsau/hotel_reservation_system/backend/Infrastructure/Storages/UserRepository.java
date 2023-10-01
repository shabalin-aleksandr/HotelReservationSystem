package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
}
