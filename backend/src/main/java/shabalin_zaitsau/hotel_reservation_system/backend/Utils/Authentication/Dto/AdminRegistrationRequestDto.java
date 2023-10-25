package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.interfaces.IAdminRegistrationRequest;

/**
 * A data transfer object representing the data needed for admin registration.
 * <p>
 * Contains fields for the admin type, name, surname, email, password, phoneNumber, country, region and city.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegistrationRequestDto extends RegistrationRequestDto implements IAdminRegistrationRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "SUPER_ADMIN")
    @NotEmpty
    private AdminType adminType;
}
