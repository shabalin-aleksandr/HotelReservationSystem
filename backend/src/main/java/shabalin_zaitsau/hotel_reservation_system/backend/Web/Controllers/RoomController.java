package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.CreateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.UpdateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.RoomExternalService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "Rooms", description = "Endpoints for managing room")
public class RoomController {

    private final RoomExternalService roomExternalService;

    @Operation(
            summary = "Get all rooms",
            description = "Get all rooms in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewRoomDto.class
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ViewRoomDto> getRooms() {
        return roomExternalService.findAllRooms();
    }

    @Operation(
            summary = "Get rooms by hotel ID",
            description = "Get all rooms in particular hotel by hotel ID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewRoomDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewRoomDto> getAllRoomsByHotelId(@PathVariable UUID hotelId) {
        return roomExternalService.findAllRoomsByHotelId(hotelId);
    }

    @Operation(
            summary = "Get one room",
            description = "Get one room by his own id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewRoomDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping(path = "/{hotelId}/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewRoomDto getRoomById(@PathVariable("hotelId") UUID hotelId, @PathVariable("roomId") UUID roomId) {
        return roomExternalService.findRoomById(hotelId, roomId);
    }

    @Operation(
            summary = "Get all available rooms ",
            description = "Get all available rooms for hotel",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewRoomDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping("/{hotelId}/available")
    public List<ViewRoomDto> findAvailableRoomsInHotelForDateRange(
            @PathVariable UUID hotelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return roomExternalService.findAvailableRoomsInHotelForDateRange(hotelId, fromDate, toDate);
    }


    @Operation(
            summary = "Create room",
            description = "Create room in database",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewRoomDto.class
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
    @PostMapping("/create/{hotelId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewRoomDto createRoom(
            @PathVariable("hotelId") UUID hotelId,
            @RequestBody CreateRoomDto createRoomDto) {
        return roomExternalService.addRoom(hotelId, createRoomDto);
    }

    @Operation(
            summary = "Update Room",
            description = "Update Room for particular hotel",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewRoomDto.class
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
    @PatchMapping("/update/{hotelId}/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewRoomDto updateRoom(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @RequestBody UpdateRoomDto updateRoomDto) {
        return roomExternalService.editRoom(hotelId, roomId, updateRoomDto);
    }

    @Operation(
            summary = "Delete room",
            description = "Delete room from database by id",
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
    @DeleteMapping("delete/{hotelId}/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoomById(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId
    ) {
        roomExternalService.removeRoomById(hotelId, roomId);
    }
}
