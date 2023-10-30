package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.RoomMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.RoomAlreadyExistsException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EventLayer.AdminExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer.FetchHotelEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EventLayer.HotelExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.EntityLayer.interfaces.IRoomReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomReadService implements IRoomReadService {

    private final RoomRepository roomRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<ViewRoomDto> findAllRooms() {
        return roomRepository
                .findAll()
                .stream()
                .map(RoomMapper::toRoomResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewRoomDto> findAllRoomsByHotelId(UUID hotelId) {
        validateHotelExists(hotelId);
        return roomRepository
                .findByHotel_HotelId(hotelId)
                .stream()
                .map(RoomMapper::toRoomResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ViewRoomDto findRoomById(UUID hotelId, UUID roomId) {
        validateHotelExists(hotelId);
        Room room = fetchRoomById(hotelId, roomId);
        return RoomMapper.toRoomResponseDto(room);
    }

    protected void validateRoomDoesNotExist(UUID hotelId, String roomNumber, CategoryType category) {
        Optional<Room> existingRoom = roomRepository.findByHotel_HotelIdAndRoomNumberAndCategory(
                hotelId, roomNumber, category);

        if (existingRoom.isPresent()) {
            throw new RoomAlreadyExistsException("A room with the same properties already exists.");
        }
    }

    protected void validateHotelExists(UUID hotelId) {
        HotelExistsCheckEvent event = new HotelExistsCheckEvent(hotelId);
        eventPublisher.publishEvent(event);
        if (!event.isHotelExists()) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " doesn't exist");
        }
    }

    protected Room fetchRoomById(UUID hotelId, UUID roomId) {
        validateHotelExists(hotelId);
        return roomRepository
                .findByHotel_HotelIdAndRoomId(hotelId, roomId)
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Room with id: %s doesn't exist in hotel with id: %s",
                            roomId,
                            hotelId
                    );
                    return new EntityNotFoundException(errorMessage);
                });
    }

    public Hotel fetchHotelById(UUID hotelId) {
        FetchHotelEvent fetchHotelEvent = new FetchHotelEvent(hotelId);
        eventPublisher.publishEvent(fetchHotelEvent);
        return fetchHotelEvent.getHotel();
    }

    public Admin validateAdminExists(UUID adminId) {
        AdminExistsCheckEvent event = new AdminExistsCheckEvent(adminId);
        eventPublisher.publishEvent(event);
        if (!event.isAdminExists()) {
            throw new EntityNotFoundException("Admin with id: " + adminId + " doesn't exist");
        }
        return event.getAdmin();
    }
}
