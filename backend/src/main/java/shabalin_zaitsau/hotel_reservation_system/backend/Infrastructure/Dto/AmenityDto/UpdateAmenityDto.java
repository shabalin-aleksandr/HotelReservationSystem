package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto;

import lombok.Getter;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityUpdate;

@Getter
@Setter
public class UpdateAmenityDto extends CreateAmenityDto implements IAmenityUpdate {}
