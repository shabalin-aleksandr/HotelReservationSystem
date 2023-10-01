package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "John")
    @NotEmpty
    private String firstName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Wick")
    @NotEmpty
    private String lastName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "john@wick.com")
    @NotNull
    @NotEmpty
    private String email;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "+420774285390")
    @NotEmpty
    private String phoneNumber;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Czech Republic")
    @NotEmpty
    private String country;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Zlinsky")
    @NotEmpty
    private String region;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Zlin")
    @NotEmpty
    private String city;
}
