package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.AmenityMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces.IAmenityReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AmenityRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.RoomRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityReadService implements IAmenityReadService {

    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<ViewAmenityDto> findAllAmenities(){
        return amenityRepository
                .findAll()
                .stream()
                .map(AmenityMapper::toAmenityResponseDto)
                .collect(Collectors.toList());
    }
    @Override
    public ViewAmenityDto findAmenityById(UUID roomId, UUID amenityId){
        if (!roomRepository.existsById(roomId)){
            throw new EntityNotFoundException("Room with id: " + roomId + " doesn't exist");
        }
        Amenity amenity = amenityRepository
                .findByRoom_RoomIdAndAmenityId(roomId, amenityId)
                .orElseThrow(() -> new EntityNotFoundException("Amenity with id" + amenityId + "doesn't exist"));
                return AmenityMapper.toAmenityResponseDto(amenity);
    }

    @Override
    public List<ViewAmenityDto> findAllAmenitiesByRoomId(UUID roomId){
        if (!roomRepository.existsById(roomId)){
            throw new EntityNotFoundException("Room with id: " + roomId + " doesn't exist");
        }
        return amenityRepository
                .findByRoom_RoomId(roomId)
                .stream()
                .map(AmenityMapper::toAmenityResponseDto)
                .collect(Collectors.toList());
    }



 }
