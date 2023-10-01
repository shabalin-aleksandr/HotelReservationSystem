package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;

@Component
public class UserMapper {

    public static ViewUserDto toUserResponseDto(User user) {
        return new ViewUserDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCountry(),
                user.getRegion(),
                user.getCity()
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
        return user;
    }
}
