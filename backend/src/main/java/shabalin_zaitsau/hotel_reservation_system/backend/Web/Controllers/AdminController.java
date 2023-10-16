package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.CreateAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.UpdateAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.AdminExternalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
@Tag(name = "Admins", description = "Endpoints for managing admins")
public class AdminController {

    private final AdminExternalService adminExternalService;

    @Operation(
            summary = "Get Admins",
            description = "Get all Admins in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAdminDto.class
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
    public List<ViewAdminDto> getAdmins() {
        return adminExternalService.findAllAdmins();
    }

    @Operation(
            summary = "Get one Admin",
            description = "Get one Admin by his own id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAdminDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping(path= "/{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewAdminDto getAdminById(@PathVariable("adminId") UUID adminId) {
        return adminExternalService.findAdminById(adminId);
    }

    @Operation(
            summary = "Create Admin",
            description = "Create Admin in database",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAdminDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PostMapping("/create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewAdminDto createAdmin(
            @PathVariable("userId") UUID userId,
            @RequestBody CreateAdminDto createAdminDto
    ) {
        return adminExternalService.addAdmin(userId, createAdminDto);
    }

    @Operation(
            summary = "Update Admin",
            description = "Update info about Admin by user id and admin id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewAdminDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PatchMapping("/update/{userId}/{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewAdminDto updateAdmin(
            @PathVariable("userId") UUID userId,
            @PathVariable("adminId") UUID adminId,
            @RequestBody UpdateAdminDto updateAdminDto
    ) {
        return adminExternalService.editAdmin(userId, adminId, updateAdminDto);
    }

    @Operation(
            summary = "Delete Admin",
            description = "Delete Admin from database by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @DeleteMapping(path = "{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("adminId") UUID adminId) {
        adminExternalService.removeAdminById(adminId);
    }
}


















