package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Constants.DateFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationDto implements IReservationCreate {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-10-16")
    @JsonFormat(pattern = DateFormat.PATTERN)
    @NotEmpty
    private Date reservationFrom;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-10-21")
    @JsonFormat(pattern = DateFormat.PATTERN)
    @NotEmpty
    private Date reservationTo;
}
