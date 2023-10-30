package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ImageServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Image;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ImageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageReadService {

    private final ImageRepository imageRepository;

    public Optional<Image> getImage(String id) {
        return imageRepository.findById(id);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
