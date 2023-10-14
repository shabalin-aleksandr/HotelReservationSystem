package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomCreate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomDto implements IRoomCreate {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "104")
    @NotEmpty
    private String roomNumber;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "DELUXE")
    @NotEmpty
    private CategoryType category;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "14000")
    @NotEmpty
    private Double pricePerNight;
}
