package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.CreateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.RoomMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.UpdateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.RoomAlreadyExistsException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.interfaces.IRoomWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomWriteService implements IRoomWriteService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final JsonPatch jsonPatch;

    @Override
    public ViewRoomDto addRoom(UUID hotelId, CreateRoomDto createRoomDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with Id: " + hotelId + " doesn't exist"));

        Optional<Room> existingRoom = roomRepository.findByHotelAndRoomNumberAndCategory(
                hotel, createRoomDto.getRoomNumber(), createRoomDto.getCategory());

        if (existingRoom.isPresent()) {
            throw new RoomAlreadyExistsException("A room with the same properties already exists.");
        }

        Room room = RoomMapper.toRoom(createRoomDto, hotel);
        return RoomMapper.toRoomResponseDto(roomRepository.save(room));
    }

    @Override
    public ViewRoomDto editRoom(UUID hotelId, UUID roomId, UpdateRoomDto updateRoomDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with Id: " + hotelId + " doesn't exist"));

        Room existingRoom = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id: " + roomId + " not found"));

        if (hotel != null && existingRoom != null) {
            existingRoom.setHotel(hotel);
            existingRoom.setRoomNumber(updateRoomDto.getRoomNumber());
            existingRoom.setCategory(updateRoomDto.getCategory());
            existingRoom.setPricePerNight(updateRoomDto.getPricePerNight());

            Room patchRoom = RoomMapper.toRoom(updateRoomDto, hotel);
            Room updatedRoom = jsonPatch.mergePatch(existingRoom, patchRoom, Room.class);
            updatedRoom.setHotel(hotel);

            updatedRoom = roomRepository.save(updatedRoom);

            return RoomMapper.toRoomResponseDto(updatedRoom);
        } else {
            throw new EntityNotFoundException("Hotel or Room not found");
        }
    }
}
