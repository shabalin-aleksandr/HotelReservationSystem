package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ReservationMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces.IReservationReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ReservationRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationReadService implements IReservationReadService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<ViewReservationDto> findAllReservation() {
        return reservationRepository
                .findAll()
                .stream()
                .map(ReservationMapper::toReservationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ViewReservationDto findReservationById(UUID reservationId) {
        Reservation reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Reservation with id: " + reservationId + " doesn't exist"
                ));
        return ReservationMapper.toReservationResponseDto(reservation);
    }
}
