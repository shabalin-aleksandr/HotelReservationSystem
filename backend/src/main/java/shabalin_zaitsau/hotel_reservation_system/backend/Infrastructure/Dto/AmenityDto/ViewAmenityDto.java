package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class ViewAmenityDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "de4376a5-7757-4b1f-989e-907a38fa8cda")
    private UUID amenityId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "WI-FI")
    @NotEmpty
    private String amenityName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Good WI-FI connection")
    @NotEmpty
    private String description;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "066167fe-63a2-11ee-8c99-0242ac120002")
    @NotEmpty
    private UUID roomId;
}
