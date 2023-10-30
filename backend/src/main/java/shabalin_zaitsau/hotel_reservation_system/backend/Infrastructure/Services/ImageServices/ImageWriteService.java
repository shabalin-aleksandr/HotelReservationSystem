package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ImageServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Image;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages.ImageRepository;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageWriteService {

    private final ImageRepository imageRepository;

    public Image save(@NotNull MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        image.setSize(file.getSize());

        return imageRepository.save(image);
    }
}
