package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;

import java.util.Optional;
import java.util.UUID;

// TODO: Leave only one method after implementing event
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByUserDetails(User user);
    Admin findAdminByUserDetails(User user);
    Optional<Admin> findByUserDetailsUserId(UUID userId);
}
