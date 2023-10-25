package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Admin;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.User;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.AdminMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Exceptions.EntitiesExeptions.EntityNotFoundException;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AdminServices.interfaces.IAdminReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.EventLayer.UserExistCheckEvent;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.AdminRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminReadService implements IAdminReadService {

    private final AdminRepository adminRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<ViewAdminDto> findAllAdmins() {
        return adminRepository
                .findAll()
                .stream()
                .map(AdminMapper::toAdminResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ViewAdminDto findAdminById(UUID adminId) {
        Admin admin = fetchAdminById(adminId);
        return AdminMapper.toAdminResponseDto(admin);
    }

    protected Admin fetchAdminById(UUID adminId) {
        return adminRepository
                .findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin with id: " + adminId + " doesn't exist"));
    }

    protected void validateUserExists(UUID userId) {
        UserExistCheckEvent event = new UserExistCheckEvent(userId);
        eventPublisher.publishEvent(event);
        if (!event.isUserExists()) {
            throw new EntityNotFoundException("User with id: " + userId + " doesn't exist");
        }
    }

    public boolean isAdminType(User user, AdminType type) {
        Optional<Admin> adminOptional = adminRepository.findByUserDetails(user);
        return adminOptional.isPresent() && adminOptional.get().getAdminType() == type;
    }
}
