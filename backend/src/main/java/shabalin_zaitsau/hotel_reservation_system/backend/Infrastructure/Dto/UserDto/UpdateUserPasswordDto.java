package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.interfaces.IUserPasswordUpdate;


@Getter
@Setter
public class UpdateUserPasswordDto implements IUserPasswordUpdate {

    @NotBlank(message = "Old password must not be blank")
    private String oldPassword;
    @NotBlank(message = "New password must not be blank")
    private String newPassword;
}
