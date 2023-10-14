package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EventLayer;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;

@Service
@RequiredArgsConstructor
public class RoomEventListener {

    private final RoomRepository roomRepository;

    @EventListener
    public void handleRoomExistsCheckEvent(@NotNull RoomExistsCheckEvent event) {
        boolean exists = roomRepository.existsByHotel_HotelIdAndRoomId(event.getHotelId(), event.getRoomId());
        event.setRoomExists(exists);
    }
}
