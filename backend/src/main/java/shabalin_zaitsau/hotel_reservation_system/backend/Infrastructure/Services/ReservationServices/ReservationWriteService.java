package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.CreateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.UpdateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces.IReservationWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ReservationRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.UserRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationWriteService implements IReservationWriteService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final JsonPatch jsonPatch;

    @Override
    public ViewReservationDto addReservation(UUID userId, CreateReservationDto createReservationDto) {
        return null;
    }

    @Override
    public ViewReservationDto editReservation(UUID reservationId, UpdateReservationDto updateReservationDto) {
        return null;
    }
}
