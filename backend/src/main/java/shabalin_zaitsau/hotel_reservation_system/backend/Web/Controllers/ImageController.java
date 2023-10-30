package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Image;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ImageDto.ImageMapper;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ImageDto.ViewImageDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.ImageExternalService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/images")
@Tag(name = "Images", description = "Endpoints for managing images")
public class ImageController {

    private final ImageExternalService imageExternalService;

    @Operation(
            summary = "Get Images (public)",
            description = "Get all Users in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewImageDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ViewImageDto> list() {
        return imageExternalService
                .getAllImages()
                .stream()
                .map(ImageMapper::toImageResponse)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Get Image (public)",
            description = "Get all Users in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewImageDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<Image> fileEntityOptional = imageExternalService.getImage(id);
        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        Image image = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .contentType(MediaType.valueOf(image.getContentType()))
                .body(image.getData());
    }

    @Operation(
            summary = "Upload Images (public)",
            description = "Upload Image in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewImageDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile image) {
        try {
            imageExternalService.save(image);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", image.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", image.getOriginalFilename()));
        }
    }
}
