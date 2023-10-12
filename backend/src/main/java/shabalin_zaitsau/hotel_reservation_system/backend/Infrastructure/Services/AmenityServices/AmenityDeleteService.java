package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.interfaces.IAmenityDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AmenityRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityDeleteService implements IAmenityDeleteService {

    private final AmenityRepository amenityRepository;

    @Override
    public void removeAmenityById(UUID amenityId){
        boolean exists = amenityRepository.existsById(amenityId);

        if (!exists){
            throw new EntityNotFoundException("Amenity with id: " + amenityId + " not found");
        }
        amenityRepository.deleteById(amenityId);
    }

}
