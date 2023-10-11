package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces;

import java.util.UUID;

public interface IReservationDeleteService {
    void removeReservationInRoomById(UUID hotelId, UUID roomId, UUID reservationId);
    void removeAllReservationForRoom(UUID hotelId, UUID roomId);
    void removeAllReservationInHotel(UUID hotelId);
}
