package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;

@Component
public class ReservationMapper {

    public static ViewReservationDto toReservationResponseDto(Reservation reservation) {
        return new ViewReservationDto(
                reservation.getReservationId(),
                reservation.getUser().getUserId(),
                reservation.getRoom().getRoomId(),
                reservation.getReservationFrom(),
                reservation.getReservationTo(),
                reservation.getTotalPrice()
        );
    }

    // TODO: Add Room
    public static Reservation toReservation(CreateReservationDto createReservationDto, User user, Room room) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setReservationFrom(createReservationDto.getReservationFrom());
        reservation.setReservationTo(createReservationDto.getReservationTo());
        return reservation;
    };
}
