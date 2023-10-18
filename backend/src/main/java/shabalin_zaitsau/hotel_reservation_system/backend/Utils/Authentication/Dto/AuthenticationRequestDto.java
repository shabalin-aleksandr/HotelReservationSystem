package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IAuthenticationRequest;

/**
 * Data transfer object representing the authentication request sent by the client.
 * <p>
 * Contains the email and password of the user trying to authenticate.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto implements IAuthenticationRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "john@wick.com")
    @NotNull
    @NotEmpty
    private String email;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "QwErTy123@")
    @NotNull
    @NotEmpty
    private String password;
}
