package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByHotelAndRoomNumberAndCategory(Hotel hotel, String roomNumber, CategoryType category);
}