package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static ViewUserDto toUserResponseDto(User user) {
        Set<ShortViewReservationDto> viewReservations = user
                .getReservations()
                .stream()
                .map(reservation -> MainDtoMapper.mapReservationToViewDto(reservation, user, null))
                .collect(Collectors.toSet());

        return getViewUserDto(user, viewReservations);
    }

    @NotNull
    private static ViewUserDto getViewUserDto(User user, Set<ShortViewReservationDto> viewReservations) {
        ViewUserDto viewUserDto = new ViewUserDto();
        viewUserDto.setUserId(user.getUserId());
        viewUserDto.setFirstName(user.getFirstName());
        viewUserDto.setLastName(user.getLastName());
        viewUserDto.setEmail(user.getEmail());
        viewUserDto.setPhoneNumber(user.getPhoneNumber());
        viewUserDto.setCountry(user.getCountry());
        viewUserDto.setRegion(user.getRegion());
        viewUserDto.setCity(user.getCity());
        viewUserDto.setReservations(viewReservations);
        return viewUserDto;
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
