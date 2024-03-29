package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.AmenityMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces.IAmenityWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AmenityRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation.PermissionValidator;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityWriteService implements IAmenityWriteService {

    private final AmenityRepository amenityRepository;
    private final AmenityReadService amenityReadService;
    private final AmenityMapper amenityMapper;
    private final PermissionValidator validator;

    @Override
    public ViewAmenityDto addAmenity(
            UUID hotelId,
            UUID roomId,
            IAmenityCreate amenityToCreate
    ) {
        amenityReadService.validateHotelExists(hotelId);
        validator.validateHotelManagementPermission(hotelId);
        amenityReadService.validateRoomExists(hotelId, roomId);
        Amenity amenity = amenityMapper.toAmenity(amenityToCreate, roomId);
        return AmenityMapper.toAmenityResponseDto(amenityRepository.save(amenity));

    }

    @Override
    public ViewAmenityDto editAmenity(
            UUID hotelId,
            UUID roomId,
            UUID amenityId,
            @NotNull IAmenityUpdate amenityToUpdate
    ) {
        validator.validateHotelManagementPermission(hotelId);
        amenityReadService.validateRoomExists(hotelId, roomId);
        Amenity existingAmenity = amenityReadService
                .fetchAmenityById(hotelId, roomId, amenityId);
        return AmenityMapper.toAmenityResponseDto(amenityRepository.save(existingAmenity));
    }
}
