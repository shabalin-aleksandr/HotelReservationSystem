package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.CreateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.UpdateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.UserDeleteService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.UserReadService;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.UserServices.UserWriteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserWriteService userWriteService;
    private final UserReadService userReadService;
    private final UserDeleteService userDeleteService;

    @Operation(
            summary = "Get users",
            description = "Get all users in database",
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
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ViewUserDto> getUsers() {
        return userReadService.findAllUsers();
    }

    @Operation(
            summary = "Get one user",
            description = "Get one user by his own id",
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
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
            }
    )
    @GetMapping(path= "/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public ViewUserDto getUserById(@PathVariable("user_id") UUID user_id) {
        return userReadService.findUserById(user_id);
    }

    @Operation(
            summary = "Create user",
            description = "Create user in database",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewUserDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewUserDto createUser(@RequestBody CreateUserDto createUserDto) {
        return userWriteService.addUser(createUserDto);
    }

    @Operation(
            summary = "Update user",
            description = "Update user's info by id",
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
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PatchMapping("/update/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public ViewUserDto update(@PathVariable UUID user_id, @RequestBody UpdateUserDto updateUserDto) {
        return userWriteService.editUser(user_id, updateUserDto);
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user from database by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @DeleteMapping(path = "{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("user_id") UUID user_id) {
        userDeleteService.removeUserById(user_id);
    }
}
