package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto;

import lombok.Getter;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationUpdate;

@Getter
@Setter
public class UpdateReservationDto extends CreateReservationDto implements IReservationUpdate {}
