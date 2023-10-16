package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

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
     * @return A ViewReservationDto representing the mapped data.
     *
     * @throws NullPointerException If either the user or room is {@code null}.
     * @see ShortViewReservationDto
     * @see Reservation
     * @see User
     * @see Room
     */
    @NotNull
    public static ShortViewReservationDto mapReservationToViewDto(@NotNull Reservation reservation) {
        ShortViewReservationDto shortViewReservationDto = new ShortViewReservationDto();
        shortViewReservationDto.setReservationId(reservation.getReservationId());
        if (reservation.getUser() != null) {
            shortViewReservationDto.setUserId(reservation.getUser().getUserId());
        }
        if (reservation.getReservedRoom() != null) {
            shortViewReservationDto.setRoomId(reservation.getReservedRoom().getRoomId());
        }
        shortViewReservationDto.setTotalDays(reservation.getTotalDays());
        shortViewReservationDto.setTotalPrice(reservation.getTotalPrice());
        return shortViewReservationDto;
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
    @NotNull
    public static ShortViewRoomDto mapRoomToViewDto(@NotNull Room room) {
        ShortViewRoomDto shortViewRoomDto = new ShortViewRoomDto();
        shortViewRoomDto.setRoomId(room.getRoomId());
        shortViewRoomDto.setRoomNumber(room.getRoomNumber());
        shortViewRoomDto.setCategory(room.getCategory());
        shortViewRoomDto.setPricePerNight(room.getPricePerNight());
        return shortViewRoomDto;
    }

    /**
     * Maps an {@link Amenity} entity to a {@link ViewAmenityDto}.
     *
     * <p>
     * This method takes a non-null Amenity entity and maps it to a ViewAmenityDto. The resulting DTO
     * contains information about the amenity, including its ID, name, and description.
     * </p>
     *
     * @param amenity The non-null Amenity entity to be mapped.
     * @return A ViewAmenityDto representing the mapped amenity data.
     *
     * @throws IllegalArgumentException if the provided amenity is null.
     *
     * @see ViewAmenityDto
     * @see Amenity
     */
    @NotNull
    public static ViewAmenityDto mapAmenityToViewDto(@NotNull Amenity amenity) {
        ViewAmenityDto viewAmenityDto = new ViewAmenityDto();
        viewAmenityDto.setAmenityId(amenity.getAmenityId());
        viewAmenityDto.setAmenityName(amenity.getAmenityName());
        viewAmenityDto.setDescription(amenity.getDescription());
        return viewAmenityDto;
    }
}
