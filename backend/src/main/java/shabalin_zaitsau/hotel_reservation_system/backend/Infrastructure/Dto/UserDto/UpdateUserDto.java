package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import lombok.Getter;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserUpdate;

@Getter
@Setter
public class UpdateUserDto extends CreateUserDto implements IUserUpdate {}
