package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.interfaces.IRoomDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation.PermissionValidator;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomDeleteService implements IRoomDeleteService {

    private final RoomRepository roomRepository;
    private final RoomReadService roomReadService;
    private final PermissionValidator validator;

    @Override
    public void removeRoomById(UUID hotelId, UUID roomId) {
        roomReadService.validateHotelExists(hotelId);
        roomReadService.fetchRoomById(hotelId, roomId);
        validator.validateHotelManagementPermission(hotelId);
        roomRepository.deleteById(roomId);
    }
}
