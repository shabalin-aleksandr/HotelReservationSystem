package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto;

import lombok.Getter;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminUpdate;

@Getter
@Setter
public class UpdateAdminDto extends CreateAdminDto implements IAdminUpdate {}
