package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

import javax.annotation.Nullable;

/**
 * Utility class for mapping entities to Data Transfer Objects (DTOs) in the main application.
 *
 * <p>
 * This class provides methods for mapping various entities to their corresponding DTOs.
 * It is responsible for creating DTO objects based on entity data.
 * </p>
 *
 * @see ViewReservationDto
 */
@Component
public class MainDtoMapper {

    /**
     * Maps a {@link Reservation}, {@link User}, and {@link Room} to a {@link ShortViewReservationDto}.
     *
     * <p>
     * This method takes a Reservation entity, a User entity, and a Room entity and maps them
     * to a ViewReservationDto. The resulting DTO contains information about the reservation,
     * user, and room.
     * </p>
     *
     * @param reservation The Reservation entity to be mapped.
     * @param user The User entity associated with the reservation.
     * @param room The Room entity associated with the reservation.
     * @return A ViewReservationDto representing the mapped data.
     *
     * @throws NullPointerException If either the user or room is {@code null}.
     * @see ShortViewReservationDto
     * @see Reservation
     * @see User
     * @see Room
     */
    public static ShortViewReservationDto mapReservationToViewDto
    (
            Reservation reservation,
            @Nullable User user,
            @Nullable Room room
    ) {
        ShortViewReservationDto viewReservationDto = new ShortViewReservationDto();
        viewReservationDto.setReservationId(reservation.getReservationId());
        assert user != null;
        viewReservationDto.setUserId(user.getUserId());
        assert room != null;
        viewReservationDto.setRoomId(room.getRoomId());
        viewReservationDto.setReservationFrom(reservation.getReservationFrom());
        viewReservationDto.setReservationTo(reservation.getReservationTo());
        return viewReservationDto;
    }

    /**
     * Maps a {@link Room} entity to a {@link ShortViewRoomDto}.
     *
     * <p>
     * This method takes a Room entity and maps it to a ShortViewRoomDto. The resulting DTO contains
     * basic information about the room, such as its ID, room number, category, and price per night.
     * </p>
     *
     * @param room The Room entity to be mapped.
     * @return A ShortViewRoomDto representing the mapped room data.
     *
     * @see ShortViewRoomDto
     * @see Room
     */
    public static ShortViewRoomDto mapRoomToViewDto(Room room) {
        ShortViewRoomDto shortViewRoomDto = new ShortViewRoomDto();
        shortViewRoomDto.setRoomId(room.getRoomId());
        shortViewRoomDto.setRoomNumber(room.getRoomNumber());
        shortViewRoomDto.setCategory(room.getCategory());
        shortViewRoomDto.setPricePerNight(room.getPricePerNight());
        return shortViewRoomDto;
    }
}
