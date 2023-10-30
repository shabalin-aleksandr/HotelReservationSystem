package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EntityLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.AdminMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.EntityLayer.interfaces.IAdminWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminWriteService implements IAdminWriteService {

    private final AdminReadService adminReadService;
    private final AdminRepository adminRepository;

    @Override
    public ViewAdminDto editAdmin(UUID adminId, @NotNull IAdminUpdate adminToUpdate) {
        Admin existingAdmin = adminReadService.fetchAdminById(adminId);
        Optional.ofNullable(adminToUpdate.getAdminType())
                .ifPresent(existingAdmin::setAdminType);
        return AdminMapper.toAdminResponseDto(adminRepository.save(existingAdmin));
    }
}
