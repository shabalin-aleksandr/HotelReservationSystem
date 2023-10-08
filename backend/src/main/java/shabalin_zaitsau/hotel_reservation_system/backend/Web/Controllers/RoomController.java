package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.CreateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.UpdateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.RoomDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.RoomReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.RoomServices.RoomWriteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "Rooms", description = "Endpoints for managing room")
public class RoomController {

    private final RoomReadService roomReadService;
    private final RoomWriteService roomWriteService;
    private final RoomDeleteService roomDeleteService;

    @Operation(
            summary = "Get rooms",
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ViewRoomDto> getRooms() {
        return roomReadService.findAllRooms();
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
    @GetMapping(path = "{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewRoomDto getRoomById(@PathVariable("roomId") UUID roomId) {
        return roomReadService.findRoomById(roomId);
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
    @PostMapping("/create/{hotelId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewRoomDto createRoom(
            @PathVariable("hotelId") UUID hotelId,
            @RequestBody CreateRoomDto createRoomDto) {
        return roomWriteService.addRoom(hotelId, createRoomDto);
    }

    @Operation(
            summary = "Update Room ",
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
    @PutMapping("/update/{hotelId}/{roomId}")
    public ViewRoomDto updateRoom(
            @PathVariable("hotelId") UUID hotelId,
            @PathVariable("roomId") UUID roomId,
            @RequestBody UpdateRoomDto updateRoomDto) {
        return roomWriteService.editRoom(hotelId, roomId, updateRoomDto);
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
    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoomById(@PathVariable("roomId") UUID roomId) {
        roomDeleteService.removeRoomById(roomId);
    }
}
