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
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.CreateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.UpdateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.ReservationExternalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@Tag(name = "Reservations", description = "Endpoints for managing reservations")
public class ReservationController {

    private final ReservationExternalService reservationExternalService;

    @Operation(
            summary = "Get all Reservations in Hotel",
            description = "Get all Reservations in particular Hotel",
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
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @GetMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewReservationDto> getAllReservationsInHotel(@PathVariable("hotelId") UUID hotelId) {
        return reservationExternalService.findAllReservationInHotel(hotelId);
    }

    @Operation(
            summary = "Get all Reservations in Room",
            description = "Get all Reservations in particular Room",
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
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN') " +
                    "or @adminReadService.isAdminType(principal, 'HOTEL_MANAGER') )"
    )
    @GetMapping("/{hotelId}/rooms/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewReservationDto> getAllReservationsInRoom(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId) {
        return reservationExternalService.findAllReservationInRoom(hotelId, roomId);
    }

    @Operation(
            summary = "Get all Reservations for User",
            description = "Get all Reservations for particular User",
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
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN'))"
    )
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewReservationDto> getAllReservationsForUser(
            @PathVariable("userId") UUID userId
    ) {
        return reservationExternalService.finaAllReservationForUser(userId);
    }

    @Operation(
            summary = "Get single Reservation",
            description = "Get one Reservation in particular Room",
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
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{hotelId}/rooms/{roomId}/reservations/{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewReservationDto getReservationById(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @PathVariable("reservationId") UUID reservationId
    ) {
        return reservationExternalService.findReservationById(hotelId, roomId, reservationId);
    }

    @Operation(
            summary = "Create Reservation",
            description = "Create Reservation for User in particular Room",
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
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("create/{hotelId}/rooms/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewReservationDto addReservationToRoomForUser(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @RequestBody() CreateReservationDto createReservationDto
    ) {
        return reservationExternalService.addReservation(hotelId, roomId, createReservationDto);
    }

    @Operation(
            summary = "Update Reservation ",
            description = "Update Reservation in particular Hotel in particular Room",
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
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PutMapping("update/{hotelId}/rooms/{roomId}/{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewReservationDto editReservationForRoomForUser(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @PathVariable("reservationId") UUID reservationId,
            @RequestBody UpdateReservationDto updateReservationDto
    ) {
        return reservationExternalService.editReservation(hotelId, roomId, reservationId, updateReservationDto);
    }

    @Operation(
            summary = "Delete single Reservation in Room",
            description = "Delete Reservation in particular Hotel in particular Room",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{hotelId}/rooms/{roomId}/reservations/{reservationId}")
    public void deleteReservationForRoom(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @PathVariable("reservationId") UUID reservationId
    ) {
        reservationExternalService.removeReservationInRoomById(hotelId, roomId, reservationId);
    }

    @Operation(
            summary = "Delete all Reservation in Room",
            description = "Delete all Reservation in particular Hotel in particular Room",
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
    @DeleteMapping("/{hotelId}/rooms/{roomId}")
    public void deleteAllReservationsInRoom(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId
    ) {
        reservationExternalService.removeAllReservationInRoom(hotelId, roomId);
    }

    @Operation(
            summary = "Delete all Reservation in Hotel",
            description = "Delete all Reservation in particular Hotel in particular Room",
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
    @DeleteMapping("/{hotelId}")
    public void deleteAllReservationInHotel(@PathVariable("hotelId") UUID hotelId) {
        reservationExternalService.removeAllReservationInHotel(hotelId);
    }
}
