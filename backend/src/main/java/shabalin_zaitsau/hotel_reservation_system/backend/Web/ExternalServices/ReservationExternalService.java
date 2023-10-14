package shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.interfaces.IReservationUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.ReservationDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.ReservationReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.ReservationWriteService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationExternalService {

    private final ReservationReadService reservationReadService;
    private final ReservationWriteService reservationWriteService;
    private final ReservationDeleteService reservationDeleteService;

    public List<ViewReservationDto> findAllReservationInHotel(UUID hotelId) {
        return reservationReadService.findAllReservationInHotel(hotelId);
    }

    public List<ViewReservationDto> findAllReservationInRoom(UUID hotelId, UUID roomId) {
        return reservationReadService.findAllReservationInRoom(hotelId, roomId);
    }

    public List<ViewReservationDto> finaAllReservationForUser(UUID userId) {
        return reservationReadService.finaAllReservationForUser(userId);
    }

    public ViewReservationDto findReservationById(UUID hotelId, UUID roomId, UUID reservationId) {
        return  reservationReadService.findReservationById(hotelId, roomId, reservationId);
    }

    public ViewReservationDto addReservation(
            UUID hotelId,
            UUID roomId,
            UUID userId,
            IReservationCreate reservationToCreate
    ) {
        return reservationWriteService.addReservation(hotelId, roomId, userId, reservationToCreate);
    }

    public ViewReservationDto editReservation(
            UUID hotelId,
            UUID roomId,
            UUID userId,
            UUID reservationId,
            @NotNull IReservationUpdate reservationToUpdate
    ) {
        return reservationWriteService.editReservation(hotelId, roomId, userId, reservationId, reservationToUpdate);
    }
    public void removeReservationInRoomById(UUID hotelId, UUID roomId, UUID reservationId) {
        reservationDeleteService.removeReservationInRoomById(hotelId, roomId, reservationId);
    }

    public void removeAllReservationInRoom(UUID hotelId, UUID roomId) {
        reservationDeleteService.removeAllReservationForRoom(hotelId, roomId);
    }

    public void removeAllReservationInHotel(UUID hotelId) {
        reservationDeleteService.removeAllReservationInHotel(hotelId);
    }
}
