package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UpdateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UpdateUserPasswordDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.UserExternalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserExternalService userExternalService;

    @Operation(
            summary = "Get Users (public)",
            description = "Get all Users in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewUserDto.class
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
    public List<ViewUserDto> getUsers() {
        return userExternalService.findAllUsers();
    }

    @Operation(
            summary = "Get one User (public)",
            description = "Get one User by his own id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewUserDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewUserDto getUserById(@PathVariable("userId") UUID userId) {
        return userExternalService.findUserById(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Update User (with Authority only)",
            description = "Update User's info by id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewUserDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PatchMapping("/update/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewUserDto updateUser(
            @PathVariable("userId") UUID userId,
            @RequestBody UpdateUserDto updateUserDto
    ) {
        return userExternalService.editUser(userId, updateUserDto);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Update User's password (with Authority only)",
            description = "Update User's password by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateUserPassword(
            @PathVariable("userId") UUID userId,
            @RequestBody UpdateUserPasswordDto passwordToUpdate
    ) {
        return userExternalService.updateUserPassword(userId, passwordToUpdate);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Delete User (with ADMIN Authority only)",
            description = "Delete User from database by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @DeleteMapping(path = "/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("userId") UUID userId) {
        userExternalService.removeUserById(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Delete User's own account (with Authority only)",
            description = "User can only delete his own account",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @DeleteMapping(path = "/deleteAccount")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteOwnAccount() {
        return  userExternalService.deleteAccount();
    }
}
