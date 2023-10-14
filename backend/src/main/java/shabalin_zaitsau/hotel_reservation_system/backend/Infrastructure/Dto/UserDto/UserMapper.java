package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserCreate;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static ViewUserDto toUserResponseDto(User user) {
        Set<ShortViewReservationDto> viewReservations = user
                .getReservations()
                .stream()
                .map(MainDtoMapper::mapReservationToViewDto)
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

    public User toUser(IUserCreate userToCreate) {
        User user = new User();
        user.setFirstName(userToCreate.getFirstName());
        user.setLastName(userToCreate.getLastName());
        user.setEmail(userToCreate.getEmail());
        user.setPhoneNumber(userToCreate.getPhoneNumber());
        user.setCountry(userToCreate.getCountry());
        user.setRegion(userToCreate.getRegion());
        user.setCity(userToCreate.getCity());
        user.setReservations(new HashSet<>());
        return user;
    }
}
