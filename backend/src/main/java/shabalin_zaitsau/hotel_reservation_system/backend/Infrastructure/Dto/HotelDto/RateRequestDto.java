package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateRequestDto {

    @Min(0)
    @Max(5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "4.7")
    @NotEmpty
    private double rating;
}
