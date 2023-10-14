package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces;

import java.util.Date;

public interface IReservationCreate {
    Date getReservationFrom();
    Date getReservationTo();
}
