package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.InvalidRatingInputException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces.IHotelWriteService;
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
    private final HotelReadService hotelReadService;
    private final HotelMapper hotelMapper;
    private final JsonPatch jsonPatch;

    @Override
    public ViewHotelDto addHotel(IHotelCreate hotelToCreate) {
        Hotel hotel = hotelMapper.toHotel(hotelToCreate);
        Set<ShortViewRoomDto> viewAvailableRooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(hotel.getAvailableRooms()));

        return HotelMapper.toHotelResponseDto(hotelRepository.save(hotel), viewAvailableRooms);
    }

    @Override
    public ViewHotelDto editHotel(UUID hotelId, IHotelUpdate hotelToUpdate) {
        Hotel existingHotel = hotelReadService.fetchHotelById(hotelId);

        Set<ShortViewRoomDto> viewAvailableRooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(existingHotel.getAvailableRooms()));

        double originalRating = existingHotel.getRating();
        Hotel patchHotel = hotelMapper.toHotel(hotelToUpdate);
        Hotel updatedHotel = jsonPatch.mergePatch(existingHotel, patchHotel, Hotel.class);
        updatedHotel.setRating(originalRating);

        return HotelMapper.toHotelResponseDto(hotelRepository.save(updatedHotel), viewAvailableRooms);
    }

    @Override
    public double putRate(UUID hotelId, double rate) {
        Hotel hotel = hotelReadService.fetchHotelById(hotelId);
        if (rate < 0 || rate > 5) {
            throw new InvalidRatingInputException("Invalid rating input: " + rate);
        }
        hotel.calculateRating(rate);
        return hotel.getRating();
    }

    @Contract(value = "!null -> param1; null -> new", pure = true)
    private static @NotNull Set<Room> getAvailableRoomsOrDefault(Set<Room> availableRooms) {
        return availableRooms != null ? availableRooms : new HashSet<>();
    }
}
