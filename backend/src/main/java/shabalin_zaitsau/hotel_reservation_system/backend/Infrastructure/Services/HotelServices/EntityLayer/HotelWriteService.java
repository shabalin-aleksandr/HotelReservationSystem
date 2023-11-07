package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ShortViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.interfaces.IHotelUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ShortViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.InvalidRatingInputException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces.IHotelWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.HotelRepository;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation.PermissionValidator;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.SecurityUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.HotelMapper.mapRoomsToViewDto;
import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper.mapAdminToViewDto;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelWriteService implements IHotelWriteService {

    private final HotelRepository hotelRepository;
    private final HotelReadService hotelReadService;
    private final HotelMapper hotelMapper;
    private final PermissionValidator validator;
    private final AdminRepository adminRepository;

    @Override
    public ViewHotelDto addHotel(IHotelCreate hotelToCreate) {
        Hotel hotel = hotelMapper.toHotel(hotelToCreate);

        UUID currentAdminId = SecurityUtils.getCurrentAdminId(adminRepository);
        Admin currentAdmin = hotelReadService.validateAdminExists(currentAdminId);
        hotel.setManagedBy(currentAdmin);

        Set<ShortViewRoomDto> viewAvailableRooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(hotel.getAvailableRooms()));
        ShortViewAdminDto viewAdmin = mapAdminToViewDto(getAdminOrDefault(hotel.getManagedBy()));

        return HotelMapper.toHotelResponseDto(hotelRepository.save(hotel), viewAvailableRooms, viewAdmin);
    }

    @Override
    public ViewHotelDto editHotel(UUID hotelId, @NotNull IHotelUpdate hotelToUpdate) {
        Hotel existingHotel = hotelReadService.fetchHotelById(hotelId);
        validator.validateAdminPermission(existingHotel.getManagedBy().getAdminId());

        Set<ShortViewRoomDto> rooms =
                mapRoomsToViewDto(getAvailableRoomsOrDefault(existingHotel.getAvailableRooms()));
        ShortViewAdminDto viewAdmin = mapAdminToViewDto(getAdminOrDefault(existingHotel.getManagedBy()));

        double originalRating = existingHotel.getRating();
        Optional.ofNullable(hotelToUpdate.getHotelName()).ifPresent(existingHotel::setHotelName);
        Optional.ofNullable(hotelToUpdate.getCountry()).ifPresent(existingHotel::setCountry);
        Optional.ofNullable(hotelToUpdate.getCity()).ifPresent(existingHotel::setCity);
        Optional.ofNullable(hotelToUpdate.getAddress()).ifPresent(existingHotel::setAddress);
        Optional.ofNullable(hotelToUpdate.getReceptionNumber()).ifPresent(existingHotel::setReceptionNumber);
        existingHotel.setRating(originalRating);

        Hotel updatedHotel = hotelRepository.save(existingHotel);
        return HotelMapper.toHotelResponseDto(updatedHotel, rooms, viewAdmin);
    }

    @Override
    public double putRate(UUID hotelId, double rate) {
        Hotel hotel = hotelReadService.fetchHotelById(hotelId);
        if (rate < 0 || rate > 5) {
            throw new InvalidRatingInputException("Invalid rating input: " + rate);
        }
        hotel.calculateRating(rate);
        hotelRepository.save(hotel);
        return hotel.getRating();
    }

    @Contract(value = "!null -> param1; null -> new", pure = true)
    @NotNull
    private static Set<Room> getAvailableRoomsOrDefault(Set<Room> availableRooms) {
        return availableRooms != null ? availableRooms : new HashSet<>();
    }

    @NotNull
    private static Admin getAdminOrDefault(Admin admin) {
        return admin != null ? admin : new Admin();
    }
}
