package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces.IReservationDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ReservationRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationDeleteService implements IReservationDeleteService {

    private final ReservationRepository reservationRepository;
    private final ReservationReadService reservationReadService;

    @Override
    public void removeReservationInRoomById(UUID hotelId, UUID roomId, UUID reservationId) {
        reservationReadService.fetchReservationById(hotelId, roomId, reservationId);
    }

    @Override
    public void removeAllReservationForRoom(UUID hotelId, UUID roomId) {
        reservationReadService.validateHotelExists(hotelId);
        reservationReadService.validateRoomExists(hotelId, roomId);
        reservationRepository.deleteAllByReservedRoom_RoomId(roomId);
    }

    @Override
    public void removeAllReservationInHotel(UUID hotelId) {
        reservationReadService.validateHotelExists(hotelId);
        reservationRepository.deleteAllByReservedRoom_Hotel_HotelId(hotelId);
    }
}
