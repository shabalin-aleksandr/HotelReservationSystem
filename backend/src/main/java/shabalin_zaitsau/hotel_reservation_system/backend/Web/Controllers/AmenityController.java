package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.CreateAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.UpdateAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.ViewAmenityDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityCreate;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AmenityDto.interfaces.IAmenityUpdate;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.AmenityExternalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/amenities")
@Tag(name = "Amenities", description = "Endpoints for managing amenity")
public class AmenityController {

    private final AmenityExternalService amenityExternalService;

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
        return amenityExternalService.findAllAmenity();
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
    @GetMapping("/{hotelId}/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewAmenityDto> getAllAmenitiesByRoomId(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId){
        return amenityExternalService.findAllAmenityByRoomId(hotelId, roomId);
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
    @GetMapping(path = "/findAmenityById/{hotelId}/{roomId}/{amenityId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewAmenityDto getAmenityById(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @PathVariable("amenityId") UUID amenityId){
        return amenityExternalService.findAmenityById(hotelId, roomId, amenityId);
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
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @PostMapping("/create/{hotelId}/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewAmenityDto createAmenity(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @RequestBody CreateAmenityDto amenityToCreate){
        return amenityExternalService.addAmenity(hotelId,roomId, amenityToCreate);
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
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @PutMapping("/update/{hotelId}/{roomId}/{amenityId}")
    public ViewAmenityDto updateAmenity(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @PathVariable("amenityId") UUID amenityId,
            @RequestBody UpdateAmenityDto amenityToUpdate){
        return amenityExternalService.editAmenity(hotelId, roomId, amenityId, amenityToUpdate);
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
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @DeleteMapping("/DeleteAmenityById/{hotelId}/{roomId}/{amenityId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAmenityInRoomById(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @PathVariable("amenityId") UUID amenityId){
        amenityExternalService.removeAmenityInRoomById(hotelId, roomId, amenityId);
    }

    @Operation(
            summary = "Delete all amenities from room",
            description = "Delete amenities from database by room id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @DeleteMapping("/DeleteAmenitiesByRoomId/{hotelId}/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAmenitiesByRoomId(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId){
        amenityExternalService.removeAllAmenityForRoom(hotelId, roomId);
    }
}
