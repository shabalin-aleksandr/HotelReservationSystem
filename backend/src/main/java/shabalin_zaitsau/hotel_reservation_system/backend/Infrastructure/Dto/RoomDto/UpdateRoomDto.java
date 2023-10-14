package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto;

import lombok.Getter;
import lombok.Setter;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomUpdate;

@Getter
@Setter
public class UpdateRoomDto extends CreateRoomDto implements IRoomUpdate {}
