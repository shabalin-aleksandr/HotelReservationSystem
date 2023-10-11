package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;

import java.util.List;
import java.util.UUID;

// TODO: Add Room ID and Hotel ID, separate method by finding reservation in hotels and in particular room
public interface IReservationReadService {
    List<ViewReservationDto> findAllReservationInHotel(UUID hotelId);
    List<ViewReservationDto> findAllReservationInRoom(UUID hotelId, UUID roomId);
    List<ViewReservationDto> finaAllReservationForUser(UUID userId);
    ViewReservationDto findReservationById(UUID hotelId, UUID roomId, UUID reservationId);
}
