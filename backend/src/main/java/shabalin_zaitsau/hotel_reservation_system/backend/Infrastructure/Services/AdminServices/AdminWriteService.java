package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.AdminMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.interfaces.IAdminUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.interfaces.IAdminWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminWriteService implements IAdminWriteService {

    private final AdminReadService adminReadService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Override
    public ViewAdminDto addAdmin(UUID userId, IAdminCreate adminToCreate) {
        adminReadService.validateUserExists(userId);
        Admin admin = adminMapper.toAdmin(adminToCreate, userId);
        return AdminMapper.toAdminResponseDto(adminRepository.save(admin));
    }

    @Override
    public ViewAdminDto editAdmin(UUID userId, UUID adminId, IAdminUpdate adminToUpdate) {
        Admin existingAdmin = adminReadService.fetchAdminById(adminId);
        adminReadService.validateUserExists(userId);
        Optional.ofNullable(adminToUpdate.getAdminType())
                .ifPresent(existingAdmin::setAdminType);
        return AdminMapper.toAdminResponseDto(adminRepository.save(existingAdmin));
    }
}
