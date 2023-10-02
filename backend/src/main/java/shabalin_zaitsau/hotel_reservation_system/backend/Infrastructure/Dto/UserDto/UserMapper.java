package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static ViewUserDto toUserResponseDto(User user) {
        Set<ViewReservationDto> viewReservations = user.getReservations().stream()
                .map(reservation -> {
                    ViewReservationDto viewReservationDto = new ViewReservationDto();
                    viewReservationDto.setReservationId(reservation.getReservationId());
                    viewReservationDto.setUserId(user.getUserId());
                    viewReservationDto.setRoomId(reservation.getRoom().getRoomId());
                    viewReservationDto.setReservationFrom(reservation.getReservationFrom());
                    viewReservationDto.setReservationTo(reservation.getReservationTo());
                    viewReservationDto.setTotalPrice(reservation.getTotalPrice());
                    return viewReservationDto;
                })
                .collect(Collectors.toSet());

        return new ViewUserDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCountry(),
                user.getRegion(),
                user.getCity(),
                viewReservations // Use the mapped Set<ViewReservationDto>
        );
    }


    public static User toUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setPhoneNumber(createUserDto.getPhoneNumber());
        user.setCountry(createUserDto.getCountry());
        user.setRegion(createUserDto.getRegion());
        user.setCity(createUserDto.getCity());
        user.setReservations(new HashSet<>());
        return user;
    }
}
