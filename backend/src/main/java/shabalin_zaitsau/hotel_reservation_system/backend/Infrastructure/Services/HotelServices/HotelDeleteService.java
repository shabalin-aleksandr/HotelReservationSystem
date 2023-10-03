package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.interfaces.IHotelDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelDeleteService implements IHotelDeleteService {

    private final HotelRepository hotelRepository;
    @Override
    public void removeHotelById(UUID hotelId) {
        hotelRepository.findById(hotelId);
        boolean exists = hotelRepository.existsById(hotelId);

        if (!exists) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " not found");
        }
        hotelRepository.deleteById(hotelId);
    }
}
