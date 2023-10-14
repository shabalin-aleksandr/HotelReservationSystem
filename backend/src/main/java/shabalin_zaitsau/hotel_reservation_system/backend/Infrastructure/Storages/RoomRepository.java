package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    boolean existsByHotel_HotelIdAndRoomId(UUID hotelId, UUID roomId);
    Optional<Room> findByHotel_HotelIdAndRoomNumberAndCategory(UUID hotelId, String roomNumber, CategoryType category);
    List<Room> findByHotel_HotelId(UUID hotelId);
    Optional<Room> findByHotel_HotelIdAndRoomId(UUID hotelId, UUID roomId);

}