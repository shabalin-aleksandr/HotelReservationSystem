package shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Image;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ImageServices.ImageDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ImageServices.ImageReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ImageServices.ImageWriteService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageExternalService {

    private final ImageReadService imageReadService;
    private final ImageWriteService imageWriteService;
    private final ImageDeleteService imageDeleteService;

    public Optional<Image> getImage(String imageId) {
        return imageReadService.getImage(imageId);
    }

    public List<Image> getAllImages() {
        return  imageReadService.getAllImages();
    }

    public Image save(MultipartFile file) throws IOException {
        return imageWriteService.save(file);
    }

    public void delete(Image image) {
        imageDeleteService.delete(image);
    }
}
