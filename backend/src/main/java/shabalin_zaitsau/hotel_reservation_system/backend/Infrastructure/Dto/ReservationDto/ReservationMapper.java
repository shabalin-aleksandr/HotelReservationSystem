package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper.mapRoomToViewDto;

@Component
public class ReservationMapper {

    public static ViewReservationDto toReservationResponseDto(Reservation reservation) {
        ShortViewRoomDto shortViewRoomDto = mapRoomToViewDto(reservation.getRoom());
        return getViewReservationDto(reservation, shortViewRoomDto);
    }

    @NotNull
    private static ViewReservationDto getViewReservationDto(Reservation reservation, ShortViewRoomDto shortViewRoomDto) {
        ViewReservationDto viewReservationDto = new ViewReservationDto();
        viewReservationDto.setReservationId(reservation.getReservationId());
        viewReservationDto.setUserId(reservation.getUser().getUserId());
        viewReservationDto.setRoomId(reservation.getRoom().getRoomId());
        viewReservationDto.setReservationFrom(reservation.getReservationFrom());
        viewReservationDto.setReservationTo(reservation.getReservationTo());
        viewReservationDto.setTotalPrice(reservation.getTotalPrice());
        viewReservationDto.setRoom(shortViewRoomDto);
        return viewReservationDto;
    }

    public static Reservation toReservation(CreateReservationDto createReservationDto, User user, Room room) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setReservationFrom(createReservationDto.getReservationFrom());
        reservation.setReservationTo(createReservationDto.getReservationTo());
        return reservation;
    };
}
