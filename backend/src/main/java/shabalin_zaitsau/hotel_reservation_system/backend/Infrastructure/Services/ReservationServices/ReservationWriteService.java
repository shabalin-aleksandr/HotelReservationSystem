package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ReservationMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces.IReservationWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ReservationRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation.PermissionValidator;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.SecurityUtils;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationWriteService implements IReservationWriteService {

    private final ReservationRepository reservationRepository;
    private final ReservationReadService reservationReadService;
    private final ReservationMapper reservationMapper;
    private final PermissionValidator validator;

    @Override
    public ViewReservationDto addReservation(
            UUID hotelId,
            UUID roomId,
            IReservationCreate reservationToCreate
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();
        reservationReadService.validateHotelExists(hotelId);
        reservationReadService.validateRoomExists(hotelId, roomId);
        reservationReadService.validateUserExists(userId);
        Reservation reservation = reservationMapper.toReservation(reservationToCreate, userId, hotelId, roomId);
        return ReservationMapper.toReservationResponseDto(reservationRepository.save(reservation));
    }


    @Override
    public ViewReservationDto editReservation(
            UUID hotelId,
            UUID roomId,
            UUID reservationId,
            @NotNull IReservationUpdate reservationToUpdate
    ) {
        Reservation existingReservation = reservationReadService
                .fetchReservationById(hotelId, roomId, reservationId);
        validator.validateReservationPermission(hotelId, existingReservation.getUser().getUserId());

        Optional.ofNullable(reservationToUpdate.getReservationFrom())
                .ifPresent(existingReservation::setReservationFrom);
        Optional.ofNullable(reservationToUpdate.getReservationTo())
                .ifPresent(existingReservation::setReservationTo);

        return ReservationMapper.toReservationResponseDto(reservationRepository.save(existingReservation));
    }
}
