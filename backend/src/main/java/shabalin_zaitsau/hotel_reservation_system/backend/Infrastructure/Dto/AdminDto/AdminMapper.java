package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Hotel;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ShortViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ShortViewUserDto;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.MainDtoMapper.MainDtoMapper.mapUserToViewDto;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class AdminMapper {

    private final EntityManager entityManager;

    @NotNull
    public static ViewAdminDto toAdminResponseDto(@NotNull Admin admin) {
        ShortViewUserDto shortViewUserDto = mapUserToViewDto(admin.getUserDetails());
        Set<ShortViewHotelDto> shortViewHotelDtoSet = mapHotelToViewDto(admin.getHotels());
        return getViewAdminDto(admin, shortViewUserDto, shortViewHotelDtoSet);

    }

    /**
     * @deprecated
     */
    @NotNull
    public static  ViewAdminDto toAdminResponseDto(
            @NotNull Admin admin,
            ShortViewUserDto viewUserDto,
            Set<ShortViewHotelDto> viewHotels
    ) {
        return getViewAdminDto(admin,
                viewUserDto != null ? viewUserDto : mapUserToViewDto(admin.getUserDetails()),
                viewHotels != null ? viewHotels : mapHotelToViewDto(admin.getHotels())
        );
    }

    @NotNull
    public static Set<ShortViewHotelDto> mapHotelToViewDto (@NotNull Set<Hotel> hotels) {
        return hotels
                .stream()
                .map(MainDtoMapper::mapHotelToViewDto)
                .collect(Collectors.toSet());
    }

    @NotNull
    private static ViewAdminDto getViewAdminDto(
            @NotNull Admin admin,
            ShortViewUserDto shortViewUserDto,
            Set<ShortViewHotelDto> shortViewHotelDto
    ) {
        ViewAdminDto viewAdminDto = new ViewAdminDto();
        viewAdminDto.setAdminId(admin.getAdminId());
        viewAdminDto.setAdminType(admin.getAdminType());
        if (admin.getUserDetails() != null) {
            viewAdminDto.setUserDetails(shortViewUserDto);
        }
        if (admin.getHotels() != null) {
            viewAdminDto.setHotels(shortViewHotelDto);
        }
        return viewAdminDto;
    }

    /**
     *
     * @deprecated
     */
    @NotNull
    public Admin toAdmin(@NotNull IAdminCreate adminToCreate, UUID userId) {
        Admin admin = new Admin();
        admin.setAdminType(adminToCreate.getAdminType());
        User userReference = entityManager.getReference(User.class, userId);
        admin.setUserDetails(userReference);
        return admin;
    }
}
