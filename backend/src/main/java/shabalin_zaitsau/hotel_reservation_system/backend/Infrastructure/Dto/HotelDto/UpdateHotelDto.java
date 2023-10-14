package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto;

import lombok.Getter;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelUpdate;

@Getter
@Setter
public class UpdateHotelDto extends CreateHotelDto implements IHotelUpdate {}
