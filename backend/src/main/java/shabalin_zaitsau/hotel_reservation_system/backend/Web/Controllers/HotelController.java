package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.CreateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.RateRequestDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.UpdateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.HotelExternalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotels")
@Tag(name = "Hotels", description = "Endpoints for managing hotels")
public class HotelController {

    private final HotelExternalService hotelExternalService;

    @Operation(
            summary = "Get hotels",
            description = "Get all hotels in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewHotelDto.class
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
    public List<ViewHotelDto> getHotels() {
        return hotelExternalService.findAllHotels();
    }

    @Operation(
            summary = "Get one hotel",
            description = "Get one hotel by his own id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewHotelDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping(path = "{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewHotelDto getHotelById(@PathVariable("hotelId") UUID hotelId) {
        return hotelExternalService.findHotelById(hotelId);
    }

    @Operation(
            summary = "Create hotel",
            description = "Create hotel in database",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewHotelDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PreAuthorize(
            "hasRole('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewHotelDto createHotel(@RequestBody CreateHotelDto createHotelDto) {
        return hotelExternalService.addHotel(createHotelDto);
    }

    @Operation(
            summary = "Update hotel",
            description = "Update hotel by id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewHotelDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PreAuthorize(
            "hasRole('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @PatchMapping("/update/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewHotelDto updateHotel(@PathVariable UUID hotelId, @RequestBody UpdateHotelDto updateHotelDto) {
        return hotelExternalService.editHotel(hotelId, updateHotelDto);
    }

    @Operation(
            summary = "Delete hotel",
            description = "Delete hotel from database by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PreAuthorize(
            "hasRole('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @DeleteMapping(path = "/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHotelById(@PathVariable("hotelId") UUID hotelId) {
        hotelExternalService.removeHotelById(hotelId);
    }

    @Operation(
            summary = "Add rating for hotel",
            description = "Rate hotel from 0 to 5",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewHotelDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public double addRate(@PathVariable UUID hotelId,
                          @Valid @RequestBody RateRequestDto rateRequestDto) {
        return hotelExternalService.putRate(hotelId, rateRequestDto.getRating());
    }
}
