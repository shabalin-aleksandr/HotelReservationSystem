package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.CreateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ReservationMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.UpdateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
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

    // TODO: Add RoomId
    @Override
    public ViewReservationDto addReservation(UUID userId, CreateReservationDto createReservationDto) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " doesn't exist"));

//        Reservation reservation = ReservationMapper.toReservation(createReservationDto, user);
//        return  ReservationMapper.toReservationResponseDto(reservationRepository.save(reservation));
        return null;
    }

    @Override
    public ViewReservationDto editReservation(UUID reservationId, UpdateReservationDto updateReservationDto) {
        return null;
    }
}
