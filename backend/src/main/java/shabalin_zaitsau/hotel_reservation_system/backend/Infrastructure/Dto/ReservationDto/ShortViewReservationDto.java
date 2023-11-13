package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortViewReservationDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "a802b400-ef01-458c-8d87-ca12ddabe822")
    @NotEmpty
    private UUID reservationId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "c3ce9db4-0e90-4cd8-9d60-52013371334b")
    @NotEmpty
    private UUID userId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "32d0343a-823f-11ee-b962-0242ac120002")
    @NotEmpty
    private UUID hotelId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "f78f08d1-8a11-4afe-952e-473bf0fc6c8a")
    @NotEmpty
    private UUID roomId;

    @Schema(requiredMode =  Schema.RequiredMode.REQUIRED, example = "5")
    private int totalDays;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "4765.80")
    @NotEmpty
    private double totalPrice;
}
