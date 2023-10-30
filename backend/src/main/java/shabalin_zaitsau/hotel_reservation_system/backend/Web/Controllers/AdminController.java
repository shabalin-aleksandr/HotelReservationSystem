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
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path= "/{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewAdminDto getAdminById(@PathVariable("adminId") UUID adminId) {
        return adminExternalService.findAdminById(adminId);
    }

    @Operation(
            summary = "Update Admin",
            description = "Update info about Admin by his id",
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
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN'))"
    )
    @PatchMapping("/update/{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewAdminDto updateAdmin(
            @PathVariable("adminId") UUID adminId,
            @RequestBody UpdateAdminDto updateAdminDto
    ) {
        return adminExternalService.editAdmin(adminId, updateAdminDto);
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
    @PreAuthorize(
            "hasAuthority('ADMIN') " +
                    "and " +
                    "(@adminReadService.isAdminType(principal, 'SUPER_ADMIN'))"
    )
    @DeleteMapping(path = "{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAdminById(@PathVariable("adminId") UUID adminId) {
        adminExternalService.removeAdminById(adminId);
    }
}
