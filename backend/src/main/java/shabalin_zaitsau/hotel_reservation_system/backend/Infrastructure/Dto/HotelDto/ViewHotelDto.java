package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ShortViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewHotelDto extends ShortViewHotelDto {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Set<ShortViewRoomDto> availableRooms;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ShortViewAdminDto managedBy;
}
