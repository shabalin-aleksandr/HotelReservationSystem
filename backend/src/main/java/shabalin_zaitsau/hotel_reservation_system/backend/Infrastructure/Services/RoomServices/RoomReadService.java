package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.RoomMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.interfaces.IRoomReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomReadService implements IRoomReadService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

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
        if (!hotelRepository.existsById(hotelId)) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " doesn't exist");
        }
        return roomRepository
                .findByHotel_HotelId(hotelId)
                .stream()
                .map(RoomMapper::toRoomResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ViewRoomDto findRoomById(UUID hotelId, UUID roomId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " doesn't exist");
        }
        Room room = roomRepository
                .findByHotel_HotelIdAndRoomId(hotelId, roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id: " + roomId + " doesn't exist"));
        return RoomMapper.toRoomResponseDto(room);
    }
}
