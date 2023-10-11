package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewRoomDto {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "066167fe-63a2-11ee-8c99-0242ac120002")
    private UUID roomId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "43fdf06d-deea-46ed-bfab-a0da3a594e62")
    @NotEmpty
    private UUID hotelId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "104")
    @NotEmpty
    private String roomNumber;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "DELUXE")
    @NotEmpty
    private CategoryType category;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3000")
    @NotEmpty
    private double pricePerNight;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Set<ShortViewReservationDto> reservations;
}
