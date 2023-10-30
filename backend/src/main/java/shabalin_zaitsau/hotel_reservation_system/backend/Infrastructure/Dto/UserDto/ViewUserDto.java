package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.Role;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ImageDto.ViewImageDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewUserDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "c3ce9db4-0e90-4cd8-9d60-52013371334b")
    private UUID userId;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ViewImageDto avatar;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "USER")
    private Role role;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Wick")
    private String firstName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Wick")
    private String lastName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "john@wick.com")
    private String email;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "+420774285390")
    private String phoneNumber;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Czech Republic")
    private String country;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Zlinsky")
    private String region;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Zlin")
    private String city;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Set<ShortViewReservationDto> reservations;
}
