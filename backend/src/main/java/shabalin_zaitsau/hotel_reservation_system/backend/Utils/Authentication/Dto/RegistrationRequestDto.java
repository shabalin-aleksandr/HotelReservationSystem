package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IRegistrationRequest;

/**
 * A data transfer object representing the data needed for user registration.
 * <p>
 * Contains fields for the user's name, surname, email, password, phoneNumber, country, region and city.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto implements IRegistrationRequest {

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

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "QwErTy123@")
    @NotNull
    @NotEmpty
    private String password;

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
