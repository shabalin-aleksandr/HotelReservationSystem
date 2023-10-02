package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty
    private Date reservationFrom;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty
    private Date reservationTo;
}
