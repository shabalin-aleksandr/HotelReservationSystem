package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.CreateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.UpdateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;

import java.util.UUID;

// TODO: Add Room ID for addReservation method
public interface IReservationWriteService {
    ViewReservationDto addReservation(UUID userId, CreateReservationDto createReservationDto);
    ViewReservationDto editReservation(UUID reservationId, UpdateReservationDto updateReservationDto);
}
