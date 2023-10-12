package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByHotelAndRoomNumberAndCategory(Hotel hotel, String roomNumber, CategoryType category);
    List<Room> findByHotel_HotelId(UUID hotelId);
    Optional<Room> findByHotel_HotelIdAndRoomId(UUID hotelId, UUID roomId);

}