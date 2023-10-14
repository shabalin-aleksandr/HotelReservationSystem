package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

import java.util.UUID;

import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper.mapRoomToViewDto;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class ReservationMapper {

    private final EntityManager entityManager;

    @NotNull
    public static ViewReservationDto toReservationResponseDto(@NotNull Reservation reservation) {
        ShortViewRoomDto shortViewRoomDto = mapRoomToViewDto(reservation.getReservedRoom());
        return getViewReservationDto(reservation, shortViewRoomDto);
    }

    @NotNull
    private static ViewReservationDto getViewReservationDto(
            @NotNull Reservation reservation,
            ShortViewRoomDto shortViewRoomDto
    ) {
        ViewReservationDto viewReservationDto = new ViewReservationDto();
        viewReservationDto.setReservationId(reservation.getReservationId());
        if (reservation.getUser() != null) {
            viewReservationDto.setUserId(reservation.getUser().getUserId());
        }
        if (reservation.getReservedRoom() != null) {
            viewReservationDto.setRoomId(reservation.getReservedRoom().getRoomId());
        }
        viewReservationDto.setReservationFrom(reservation.getReservationFrom());
        viewReservationDto.setReservationTo(reservation.getReservationTo());
        viewReservationDto.setTotalDays(reservation.getTotalDays());
        viewReservationDto.setTotalPrice(reservation.getTotalPrice());
        viewReservationDto.setRoom(shortViewRoomDto);
        return viewReservationDto;
    }

    @NotNull
    public Reservation toReservation(@NotNull IReservationCreate reservationToCreate, UUID userId, UUID roomId) {
        Reservation reservation = new Reservation();
        User userReference = entityManager.getReference(User.class, userId);
        reservation.setUser(userReference);
        Room roomReference = entityManager.getReference(Room.class, roomId);
        reservation.setReservedRoom(roomReference);
        reservation.setReservationFrom(reservationToCreate.getReservationFrom());
        reservation.setReservationTo(reservationToCreate.getReservationTo());
        reservation.setTotalDays(reservation.getTotalDays());
        return reservation;
    }
}
