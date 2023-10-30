package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortViewAdminDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "c3ce9db4-0e90-4cd8-9d60-52013371334b")
    private UUID adminId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "SUPER_ADMIN")
    private AdminType adminType;
}
