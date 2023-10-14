package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelCreate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHotelDto implements IHotelCreate {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Grand Hotel")
    @NotEmpty
    private String hotelName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Czech Republic")
    @NotEmpty
    private String country;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Brno")
    @NotEmpty
    private String city;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Benesova 19")
    @NotEmpty
    private String address;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "574-435-254")
    @NotEmpty
    private String receptionNumber;
}
