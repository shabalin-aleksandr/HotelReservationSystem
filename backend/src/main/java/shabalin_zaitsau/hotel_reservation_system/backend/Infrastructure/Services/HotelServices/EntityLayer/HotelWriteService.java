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
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.InvalidRatingInputException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces.IHotelWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;

import java.util.HashSet;
import java.util.Optional;
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

    @Override
    public ViewHotelDto addHotel(IHotelCreate hotelToCreate) {
        Hotel hotel = hotelMapper.toHotel(hotelToCreate);
        Set<ShortViewRoomDto> viewAvailableRooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(hotel.getAvailableRooms()));

        return HotelMapper.toHotelResponseDto(hotelRepository.save(hotel), viewAvailableRooms);
    }

    @Override
    public ViewHotelDto editHotel(UUID hotelId, @NotNull IHotelUpdate hotelToUpdate) {
        Hotel existingHotel = hotelReadService.fetchHotelById(hotelId);

        Set<ShortViewRoomDto> rooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(existingHotel.getAvailableRooms()));

        double originalRating = existingHotel.getRating();
        Optional.ofNullable(hotelToUpdate.getHotelName()).ifPresent(existingHotel::setHotelName);
        Optional.ofNullable(hotelToUpdate.getCountry()).ifPresent(existingHotel::setCountry);
        Optional.ofNullable(hotelToUpdate.getCity()).ifPresent(existingHotel::setCity);
        Optional.ofNullable(hotelToUpdate.getAddress()).ifPresent(existingHotel::setAddress);
        Optional.ofNullable(hotelToUpdate.getReceptionNumber()).ifPresent(existingHotel::setReceptionNumber);
        existingHotel.setRating(originalRating);

        return HotelMapper.toHotelResponseDto(hotelRepository.save(existingHotel), rooms);
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
