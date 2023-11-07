package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ImageDto;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Image;

@Component
public class ImageMapper {

    @NotNull
    public static ViewImageDto toImageResponse(@NotNull Image image) {
        String downloadURL = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/images/")
                .path(image.getId())
                .toUriString();

        ViewImageDto imageResponseDto = new ViewImageDto();
        imageResponseDto.setUrl(downloadURL);

        return imageResponseDto;
    }
}
