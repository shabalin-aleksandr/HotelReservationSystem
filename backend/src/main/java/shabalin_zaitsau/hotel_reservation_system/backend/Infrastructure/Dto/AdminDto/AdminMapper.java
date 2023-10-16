package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ShortViewUserDto;

import java.util.UUID;

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
        return getViewAdminDto(admin, shortViewUserDto);

    }

    @NotNull
    private static ViewAdminDto getViewAdminDto(@NotNull Admin admin, ShortViewUserDto shortViewUserDto) {
        ViewAdminDto viewAdminDto = new ViewAdminDto();
        viewAdminDto.setAdminId(admin.getAdminId());
        viewAdminDto.setAdminType(admin.getAdminType());
        if (admin.getUserDetails() != null) {
            viewAdminDto.setUserDetails(shortViewUserDto);
        }
        return viewAdminDto;
    }

    @NotNull
    public Admin toAdmin(@NotNull IAdminCreate adminToCreate, UUID userId) {
        Admin admin = new Admin();
        admin.setAdminType(adminToCreate.getAdminType());
        User userReference = entityManager.getReference(User.class, userId);
        admin.setUserDetails(userReference);
        return admin;
    }
}
