package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ImageServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Image;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ImageRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageDeleteService {

    private final ImageRepository imageRepository;

    public void delete(Image image) {
        imageRepository.delete(image);
    }
}
