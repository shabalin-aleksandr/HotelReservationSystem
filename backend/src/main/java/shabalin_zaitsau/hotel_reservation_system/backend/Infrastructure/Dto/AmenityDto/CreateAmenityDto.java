package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityCreate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAmenityDto implements IAmenityCreate {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "WI-FI connection")
    @NotEmpty
    private String amenityName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Good WI-FI connection")
    @NotEmpty
    private String description;
}
