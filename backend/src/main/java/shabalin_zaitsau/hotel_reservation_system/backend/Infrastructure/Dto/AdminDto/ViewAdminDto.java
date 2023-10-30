package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ShortViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ShortViewUserDto;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewAdminDto extends ShortViewAdminDto {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ShortViewUserDto userDetails;

    private Set<ShortViewHotelDto> hotels;
}
