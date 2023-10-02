package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.CreateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.ReservationReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.ReservationServices.ReservationWriteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@Tag(name = "Reservations", description = "Endpoints for managing reservations")
public class ReservationController {

    private final ReservationReadService reservationReadService;
    private final ReservationWriteService reservationWriteService;

    @Operation(
            summary = "Get reservations",
            description = "Get all reservations in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewReservationDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ViewReservationDto> getReservations() {
        return reservationReadService.findAllReservation();
    }

    @Operation(
            summary = "Get one reservation",
            description = "Get one reservation by id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewReservationDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping(path = "/{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewReservationDto getReservationById(@PathVariable("reservationId") UUID reservationId) {
        return reservationReadService.findReservationById(reservationId);
    }

    @Operation(
            summary = "Create reservation",
            description = "Create reservation for particular user",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewReservationDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PostMapping(path = "/create/{userId}")
    public ViewReservationDto createReservation(
            @PathVariable("userId") UUID userId,
            @RequestBody CreateReservationDto createReservationDto
            ) {
        return reservationWriteService.addReservation(userId, createReservationDto);
    }
}
