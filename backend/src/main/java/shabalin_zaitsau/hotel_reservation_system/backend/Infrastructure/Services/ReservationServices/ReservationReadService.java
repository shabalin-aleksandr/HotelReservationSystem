package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ReservationMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer.HotelExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.interfaces.IReservationReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EventLayer.RoomExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EventLayer.UserExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ReservationRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation.PermissionValidator;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationReadService implements IReservationReadService {

    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PermissionValidator validator;

    @Override
    public List<ViewReservationDto> findAllReservationInHotel(UUID hotelId) {
        validateHotelExists(hotelId);
        validator.validateHotelManagementPermission(hotelId);
        return reservationRepository
                .findAllByReservedRoom_Hotel_HotelId(hotelId)
                .stream()
                .map(ReservationMapper::toReservationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewReservationDto> findAllReservationInRoom(UUID hotelId, UUID roomId) {
        validateHotelExists(hotelId);
        validateRoomExists(hotelId, roomId);
        validator.validateHotelManagementPermission(hotelId);
        return reservationRepository
                .findByReservedRoom_Hotel_HotelIdAndReservedRoom_RoomId(hotelId, roomId)
                .stream()
                .map(ReservationMapper::toReservationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewReservationDto> finaAllReservationForUser(UUID userId) {
        validateUserExists(userId);
        validator.validateUserAccess(userId);
        return reservationRepository
                .findAllByUser_UserId(userId)
                .stream()
                .map(ReservationMapper::toReservationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ViewReservationDto findReservationById(UUID hotelId, UUID roomId, UUID reservationId) {
        validateHotelExists(hotelId);
        validateRoomExists(hotelId, roomId);
        Reservation reservation = fetchReservationById(hotelId, roomId, reservationId);
        validator.validateReservationPermission(hotelId, reservation.getUser().getUserId());
        return ReservationMapper.toReservationResponseDto(reservation);
    }

    protected void validateHotelExists(UUID hotelId) {
        HotelExistsCheckEvent event = new HotelExistsCheckEvent(hotelId);
        eventPublisher.publishEvent(event);
        if (!event.isHotelExists()) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " doesn't exist");
        }
    }

    protected void validateRoomExists(UUID hotelId, UUID roomId) {
        RoomExistsCheckEvent event = new RoomExistsCheckEvent(this, hotelId, roomId);
        eventPublisher.publishEvent(event);
        if (!event.isRoomExists()) {
            throw new EntityNotFoundException("Room with id: " + roomId + " doesn't exist");
        }
    }

    protected void validateUserExists(UUID userId) {
        UserExistsCheckEvent event = new UserExistsCheckEvent(userId);
        eventPublisher.publishEvent(event);
        if (!event.isUserExists()) {
            throw new EntityNotFoundException("User with id: " + userId + " doesn't exist");
        }
    }

    protected Reservation fetchReservationById(UUID hotelId, UUID roomId, UUID reservationId) {
        validateHotelExists(hotelId);
        validateRoomExists(hotelId, roomId);
        return reservationRepository
                .findByReservedRoom_Hotel_HotelIdAndReservedRoom_RoomIdAndReservationId(
                        hotelId, roomId, reservationId
                )
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Reservation %s for room %s doesn't exist in hotel with id: %s",
                            reservationId,
                            roomId,
                            hotelId
                    );
                    return new EntityNotFoundException(errorMessage);
                });

    }
}
