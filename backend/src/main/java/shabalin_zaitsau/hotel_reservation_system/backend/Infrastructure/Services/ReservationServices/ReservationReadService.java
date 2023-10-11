package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces.IReservationReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ReservationRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationReadService implements IReservationReadService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public List<ViewReservationDto> findAllReservationInHotel(UUID hotelId) {
        return null;
    }

    @Override
    public List<ViewReservationDto> findAllReservationInRoom(UUID hotelId, UUID roomId) {
        return null;
    }

    @Override
    public List<ViewReservationDto> finaAllReservationForUser(UUID userId) {
        return null;
    }

    @Override
    public ViewReservationDto findReservationById(UUID hotelId, UUID roomId, UUID reservationId) {
        return null;
    }
}
