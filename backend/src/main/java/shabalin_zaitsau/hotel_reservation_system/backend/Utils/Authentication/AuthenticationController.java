package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.AuthenticationRequestDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.AuthenticationResponseDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.RegistrationRequestDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Service.AuthenticationService;

/**
 * The {@link AuthenticationController} class handles HTTP requests related to user authentication and registration.
 * Controller with endpoints mapped to {@code "/api/auth"} path.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authorisation & Registration",
        description = "Endpoints for managing User Authorisation and Registration"
)
public class AuthenticationController {

    /**
     * An instance of the {@link AuthenticationService} class used to handle authentication and registration requests.
     */
    private final AuthenticationService service;

    /**
     * Processes a registration request and returns a response with a JWT token.
     * The request body should contain user registration data in the {@link RegistrationRequestDto} format.
     *
     * @param request A {@link RegistrationRequestDto} object containing user registration data.
     * @return A {@link ResponseEntity} object with the HTTP status code and an {@link AuthenticationResponseDto} object
     * containing a JWT token.
     */
    @Operation(
            summary = "Register User",
            description = "Register User in database and return JWT token",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponseDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegistrationRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(
            summary = "Register Admin",
            description = "Register Admin in database and return JWT token",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponseDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PostMapping("/admin/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponseDto> registerAdmin(
            @RequestBody RegistrationRequestDto request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    /**
     * Processes an authentication request and returns a response with a JWT token.
     * The request body should contain user authentication data in the {@link AuthenticationRequestDto} format.
     *
     * @param request A {@link AuthenticationRequestDto} object containing user authentication data.
     * @return A {@link ResponseEntity} object with the HTTP status code and an {@link AuthenticationResponseDto} object
     * containing a JWT token.
     */
    @Operation(
            summary = "User's Authorisation",
            description = "Authorize User or Admin and return JWT token",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponseDto.class
                                    )
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content)
            }
    )
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
