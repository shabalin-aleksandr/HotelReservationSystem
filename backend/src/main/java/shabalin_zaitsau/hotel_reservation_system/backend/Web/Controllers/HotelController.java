package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.CreateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.RateRequestDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.UpdateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.HotelDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.HotelReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.HotelWriteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotels")
@Tag(name = "Hotels", description = "Endpoints for managing hotels")
public class HotelController {

    private final HotelReadService hotelReadService;
    private final HotelWriteService hotelWriteService;
    private final HotelDeleteService hotelDeleteService;

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
        return hotelReadService.findAllHotels();
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
        return hotelReadService.findHotelById(hotelId);
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
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewHotelDto createUser(@RequestBody CreateHotelDto createHotelDto) {
        return hotelWriteService.addHotel(createHotelDto);
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
    @PatchMapping("/update/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewHotelDto update(@PathVariable UUID hotelId, @RequestBody UpdateHotelDto updateHotelDto) {
        return hotelWriteService.editHotel(hotelId, updateHotelDto);
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
    @DeleteMapping(path = "/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("hotelId") UUID hotelId) {
        hotelDeleteService.removeHotelById(hotelId);
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
    @PatchMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public double addRate(@PathVariable UUID hotelId,
                          @Valid @RequestBody RateRequestDto rateRequestDto) {
        return hotelWriteService.putRate(hotelId, rateRequestDto.getRating());
    }
}
