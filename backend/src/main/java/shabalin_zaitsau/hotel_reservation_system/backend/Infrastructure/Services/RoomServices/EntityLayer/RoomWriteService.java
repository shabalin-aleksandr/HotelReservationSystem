package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Reservation;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ShortViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.RoomMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces.IRoomUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.interfaces.IRoomWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.RoomMapper.mapAmenityToViewDto;
import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.RoomMapper.mapReservationToViewDto;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomWriteService implements IRoomWriteService {

    private final RoomRepository roomRepository;
    private final RoomReadService roomReadService;
    private final RoomMapper roomMapper;

    @Override
    public ViewRoomDto addRoom(UUID hotelId, @NotNull IRoomCreate roomToCreate) {
        roomReadService.validateHotelExists(hotelId);
        roomReadService
                .validateRoomDoesNotExist(
                        hotelId,
                        roomToCreate.getRoomNumber(),
                        roomToCreate.getCategory()
                );
        Room room = roomMapper.toRoom(roomToCreate, hotelId);
        Set<ShortViewReservationDto> reservations =
                mapReservationToViewDto(getReservationInRoom(room.getReservations()));
        Set<ViewAmenityDto> amenities =
                mapAmenityToViewDto(getAmenitiesInRoom(room.getAvailableAmenities()));

        return RoomMapper.toRoomResponseDto(roomRepository.save(room), reservations, amenities);
    }

    @Override
    public ViewRoomDto editRoom(UUID hotelId, UUID roomId, @NotNull IRoomUpdate roomToUpdate) {
        Room existingRoom = roomReadService.fetchRoomById(hotelId, roomId);

        roomReadService.validateRoomDoesNotExist(
                hotelId,
                roomToUpdate.getRoomNumber(),
                roomToUpdate.getCategory()
        );

        Set<ShortViewReservationDto> reservations =
                mapReservationToViewDto(getReservationInRoom(existingRoom.getReservations()));
        Set<ViewAmenityDto> amenities =
                mapAmenityToViewDto(getAmenitiesInRoom(existingRoom.getAvailableAmenities()));

        Optional.ofNullable(roomToUpdate.getRoomNumber()).ifPresent(existingRoom::setRoomNumber);
        Optional.ofNullable(roomToUpdate.getCategory()).ifPresent(existingRoom::setCategory);
        Optional.ofNullable(roomToUpdate.getPricePerNight()).ifPresent(existingRoom::setPricePerNight);

        return RoomMapper.toRoomResponseDto(roomRepository.save(existingRoom), reservations, amenities);
    }

    @Contract(value = "!null -> param1; null -> new", pure = true)
    @NotNull
    private static Set<Reservation> getReservationInRoom(Set<Reservation> reservations) {
        return reservations != null ? reservations : new HashSet<>();
    }

    @Contract(value = "!null -> param1; null -> new", pure = true)
    @NotNull
    private static Set<Amenity> getAmenitiesInRoom(Set<Amenity> amenities) {
        return amenities != null ? amenities : new HashSet<>();
    }
}
