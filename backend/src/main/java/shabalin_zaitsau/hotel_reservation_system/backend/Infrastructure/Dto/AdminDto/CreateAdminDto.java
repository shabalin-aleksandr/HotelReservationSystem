package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminCreate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminDto implements IAdminCreate {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "SUPER_ADMIN")
    @NotEmpty
    private AdminType adminType;
}
