package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.CreateAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.UpdateAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.AmenityDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.AmenityReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.AmenityServices.AmenityWriteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.RoomDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.RoomReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.RoomWriteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/amenities")
@Tag(name = "Amenities", description = "Endpoints for managing amenity")
public class AmenityController {

    private final AmenityReadService amenityReadService;
    private final AmenityWriteService amenityWriteService;
    private final AmenityDeleteService amenityDeleteService;

    @Operation(
            summary = "Get all amenities",
            description = "Get all amenities in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAmenityDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ViewAmenityDto> getAmenities(){
        return amenityReadService.findAllAmenities();
    }

    @Operation(
            summary = "Get amenities by room ID",
            description = "Get all amenities in particular room by room ID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAmenityDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewAmenityDto> getAllAmenitiesByRoomId(@PathVariable UUID roomId){
        return amenityReadService.findAllAmenitiesByRoomId(roomId);
    }

    @Operation(
            summary = "Get one amenity",
            description = "Get one amenity by his own id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAmenityDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping(path = "/{roomId}/{amenityId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewAmenityDto getAmenityById(@PathVariable("roomId") UUID roomId, @PathVariable("amenityId") UUID amenityId){
        return amenityReadService.findAmenityById(roomId,amenityId);
    }

    @Operation(
            summary = "Create amenity",
            description = "Create amenity in database",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAmenityDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PostMapping("/create/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewAmenityDto createAmenity(
            @PathVariable("roomId") UUID roomId,
            @RequestBody CreateAmenityDto createAmenityDto){
        return amenityWriteService.addAmenity(roomId, createAmenityDto);
    }

    @Operation(
            summary = "Update amenity ",
            description = "Update Amenity for particular room",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAmenityDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PutMapping("/update/{roomId}/{amenityId}")
    public ViewAmenityDto updateAmenity(
            @PathVariable("roomId") UUID roomId,
            @PathVariable("amenityId") UUID amenityId,
            @PathVariable UpdateAmenityDto updateAmenityDto){
        return amenityWriteService.editAmenity(roomId,amenityId,updateAmenityDto);
    }

    @Operation(
            summary = "Delete amenity",
            description = "Delete amenity from database by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @DeleteMapping("/{amenityId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAmenityById(@PathVariable("amenityId") UUID amenityId){
        amenityDeleteService.removeAmenityById(amenityId);
    }



}
