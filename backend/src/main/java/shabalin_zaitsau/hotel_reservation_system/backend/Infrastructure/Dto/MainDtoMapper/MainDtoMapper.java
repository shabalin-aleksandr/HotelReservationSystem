package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ShortViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ShortViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ShortViewUserDto;

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
        if (reservation.getHotel() != null) {
            shortViewReservationDto.setHotelId(reservation.getHotel().getHotelId());
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

    /**
     * Maps a User entity to a ShortViewUserDto for a concise view.
     *
     * @param user The User entity to be mapped.
     * @return A ShortViewUserDto containing a subset of user information for a concise view.
     *
     * @throws NullPointerException if the provided user is null.
     */
    @NotNull
    public static ShortViewUserDto mapUserToViewDto(@NotNull User user) {
        ShortViewUserDto shortViewUserDto = new ShortViewUserDto();
        shortViewUserDto.setUserId(user.getUserId());
        shortViewUserDto.setFirstName(user.getFirstName());
        shortViewUserDto.setLastName(user.getLastName());
        shortViewUserDto.setEmail(user.getEmail());
        shortViewUserDto.setPhoneNumber(user.getPhoneNumber());
        shortViewUserDto.setCountry(user.getCountry());
        shortViewUserDto.setRegion(user.getRegion());
        shortViewUserDto.setCity(user.getCity());
        return shortViewUserDto;
    }

    /**
     * Maps an Admin entity to a ShortViewAdminDto for a concise view.
     *
     * @param admin The Admin entity to be mapped.
     * @return A ShortViewAdminDto containing a subset of user information for a concise view.
     *
     * @throws NullPointerException if the provided admin is null.
     */
    @NotNull
    public static ShortViewAdminDto mapAdminToViewDto(@NotNull Admin admin) {
        ShortViewAdminDto shortViewAdminDto = new ShortViewAdminDto();
        shortViewAdminDto.setAdminId(admin.getAdminId());
        shortViewAdminDto.setAdminType(admin.getAdminType());
        return shortViewAdminDto;
    }

    /**
     * Maps a Hotel entity to a ShortViewHotelDto for a concise view.
     *
     * @param hotel The Hotel entity to be mapped.
     * @return A ShortViewHotelDto containing a subset of user information for a concise view.
     *
     * @throws NullPointerException if the provided hotel is null.
     */
    @NotNull
    public static ShortViewHotelDto mapHotelToViewDto(@NotNull Hotel hotel) {
        ShortViewHotelDto shortViewHotelDto = new ShortViewHotelDto();
        shortViewHotelDto.setHotelId(hotel.getHotelId());
        shortViewHotelDto.setHotelName(hotel.getHotelName());
        shortViewHotelDto.setCountry(hotel.getCountry());
        shortViewHotelDto.setCity(hotel.getCity());
        shortViewHotelDto.setAddress(hotel.getAddress());
        shortViewHotelDto.setReceptionNumber(hotel.getReceptionNumber());
        shortViewHotelDto.setRating(hotel.getRating());
        return shortViewHotelDto;
    }
}
