package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.CreateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.UpdateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.InvalidRatingInputException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.interfaces.IHotelWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.JsonPatch;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper.mapRoomsToViewDto;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelWriteService implements IHotelWriteService {

    private final HotelRepository hotelRepository;
    private final JsonPatch jsonPatch;

    @Override
    public ViewHotelDto addHotel(CreateHotelDto createHotelDto) {
        Hotel hotel = HotelMapper.toHotel(createHotelDto);
        Set<ShortViewRoomDto> viewAvailableRooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(hotel.getAvailableRooms()));

        return HotelMapper.toHotelResponseDto(hotelRepository.save(hotel), viewAvailableRooms);
    }

    @Override
    public ViewHotelDto editHotel(UUID hotelId, UpdateHotelDto updateHotelDto) {
        Hotel existingHotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with id: " + hotelId + " not found"));

        Set<ShortViewRoomDto> viewAvailableRooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(existingHotel.getAvailableRooms()));

        double originalRating = existingHotel.getRating();
        Hotel patchHotel = HotelMapper.toHotel(updateHotelDto);
        Hotel updatedHotel = jsonPatch.mergePatch(existingHotel, patchHotel, Hotel.class);
        updatedHotel.setRating(originalRating);

        return HotelMapper.toHotelResponseDto(hotelRepository.save(updatedHotel), viewAvailableRooms);
    }


    @Override
    public double putRate(UUID hotelId, double rate) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with id: " + hotelId + " not found"));
        if (rate < 0 || rate > 5) {
            throw new InvalidRatingInputException("Invalid rating input: " + rate);
        }
        hotel.calculateRating(rate);
        return hotel.getRating();
    }

    private static Set<Room> getAvailableRoomsOrDefault(Set<Room> availableRooms) {
        return availableRooms != null ? availableRooms : new HashSet<>();
    }
}
