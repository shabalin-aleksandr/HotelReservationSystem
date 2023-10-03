package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.CreateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.UpdateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.interfaces.IHotelWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelWriteService implements IHotelWriteService {

    private final HotelRepository hotelRepository;
    private final JsonPatch jsonPatch;


    @Override
    public ViewHotelDto addHotel(CreateHotelDto createHotelDto) {
        Hotel hotel = HotelMapper.toHotel(createHotelDto);
        return HotelMapper.toHotelResponseDto(hotelRepository.save(hotel));
    }

    @Override
    public ViewHotelDto editHotel(UUID hotelId, UpdateHotelDto updateHotelDto) {
        Hotel existingHotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with id: " + hotelId + " not found"));

        Hotel patchHotel = HotelMapper.toHotel(updateHotelDto);
        Hotel updatedHotel = jsonPatch.mergePatch(existingHotel, patchHotel, Hotel.class);

        return HotelMapper.toHotelResponseDto(hotelRepository.save(updatedHotel));
    }
}
