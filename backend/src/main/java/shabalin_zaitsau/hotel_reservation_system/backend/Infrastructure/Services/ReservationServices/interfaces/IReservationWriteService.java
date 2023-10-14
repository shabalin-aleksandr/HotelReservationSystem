package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationUpdate;

import java.util.UUID;

public interface IReservationWriteService {
    ViewReservationDto addReservation(
            UUID hotelId, UUID roomId, UUID userId, IReservationCreate reservationToCreate);
    ViewReservationDto editReservation(
            UUID hotelId, UUID roomId, UUID userId, UUID reservationId, IReservationUpdate reservationToUpdate);
}
