package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EventLayer.AdminExistsCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces.IHotelReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelReadService implements IHotelReadService {

    private final HotelRepository hotelRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<ViewHotelDto> findAllHotels() {
        return hotelRepository
                .findAll()
                .stream()
                .map(HotelMapper::toHotelResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ViewHotelDto findHotelById(UUID hotelId) {
        Hotel hotel = fetchHotelById(hotelId);
        return HotelMapper.toHotelResponseDto(hotel);
    }

    public Hotel fetchHotelById(UUID hotelId) {
        return hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with id: " + hotelId + " not found"));
    }

    protected Admin validateAdminExists(UUID userId) {
        AdminExistsCheckEvent event = new AdminExistsCheckEvent(userId);
        eventPublisher.publishEvent(event);
        if (!event.isAdminExists()) {
            throw new EntityNotFoundException("Admin with id: " + userId + " doesn't exist");
        }
        return event.getAdmin();
    }
}
