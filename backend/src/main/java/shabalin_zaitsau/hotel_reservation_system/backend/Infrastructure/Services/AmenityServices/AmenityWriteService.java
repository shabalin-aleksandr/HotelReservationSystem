package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.AmenityMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.CreateAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.UpdateAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.RoomMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.AmenityAlreadyExistsException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces.IAmenityWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AmenityRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityWriteService implements IAmenityWriteService {

    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;
    private final JsonPatch jsonPatch;

    @Override
    public ViewAmenityDto addAmenity(UUID roomId, CreateAmenityDto createAmenityDto) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with Id: " + roomId + " doesn't exist"));

        Optional<Amenity> existingAmenity = amenityRepository.findByRoomAndAmenityName(room, createAmenityDto.getAmenityName());

        if (existingAmenity.isPresent()) {
            throw new AmenityAlreadyExistsException("A amenity with the same properties already exists.");
        }

        Amenity amenity = AmenityMapper.toAmenity(createAmenityDto, room);
        return AmenityMapper.toAmenityResponseDto(amenityRepository.save(amenity));
    }

    @Override
    public ViewAmenityDto editAmenity(UUID roomId, UUID amenityId, UpdateAmenityDto updateAmenityDto) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with Id: " + roomId + " doesn't exist"));

        Amenity existingAmenity = amenityRepository
                .findById(amenityId)
                .orElseThrow(() -> new EntityNotFoundException("Amenity with id: " + amenityId + " not found"));

        if (room != null && existingAmenity != null) {
            existingAmenity.setRoom(room);
            existingAmenity.setAmenityName(updateAmenityDto.getAmenityName());
            existingAmenity.setDescription(updateAmenityDto.getDescription());

            Amenity patchAmenity = AmenityMapper.toAmenity(updateAmenityDto, room);
            Amenity updatedAmenity = jsonPatch.mergePatch(existingAmenity, patchAmenity, Amenity.class);
            updatedAmenity.setRoom(room);

            updatedAmenity = amenityRepository.save(updatedAmenity);

            return AmenityMapper.toAmenityResponseDto(updatedAmenity);
        } else {
            throw new EntityNotFoundException("Room or Amenity not found");
        }
    }
}
