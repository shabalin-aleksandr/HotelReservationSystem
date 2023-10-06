package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.interfaces.IRoomDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomDeleteService implements IRoomDeleteService {

    private final RoomRepository roomRepository;

    @Override
    public void removeRoomById(UUID roomId){
        boolean exists = roomRepository.existsById(roomId);

        if (!exists){
            throw new EntityNotFoundException("Room with id: " + roomId + "not found");
        }
        roomRepository.deleteById(roomId);
    }
}
