package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByUser_UserId(UUID userId);
    List<Reservation> findAllByReservedRoom_Hotel_HotelId(UUID hotelId);
    List<Reservation> findByReservedRoom_Hotel_HotelIdAndReservedRoom_RoomId(UUID hotelId, UUID roomId);
    Optional<Reservation> findByReservedRoom_Hotel_HotelIdAndReservedRoom_RoomIdAndReservationId(
            UUID hotelId, UUID roomId, UUID reservationId
    );
    void deleteAllByReservedRoom_RoomId(UUID roomId);

    void deleteAllByReservedRoom_Hotel_HotelId(UUID hotelId);
}
