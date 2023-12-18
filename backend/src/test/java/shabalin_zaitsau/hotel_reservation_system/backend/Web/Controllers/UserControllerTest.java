package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.Role;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.AuthenticationController;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.AuthenticationResponseDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Dto.RegistrationRequestDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Service.AuthenticationService;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.UserExternalService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

class UserControllerTest {

    private MockMvc mockMvcUserController;
    private MockMvc mockMvcAuthenticationController;
    private ObjectMapper objectMapper;

    @Mock
    private UserExternalService userExternalService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private AuthenticationController authenticationController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvcUserController = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvcAuthenticationController = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getUsersTest() throws Exception {
        ViewUserDto userDto = new ViewUserDto(
                UUID.randomUUID(),
                null,
                Role.USER,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                "USA",
                "California",
                "Los Angeles" ,
                null
        );

        List<ViewUserDto> users = List.of(userDto);
        when(userExternalService.findAllUsers()).thenReturn(users);

        mockMvcUserController.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").isNotEmpty())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));

        verify(userExternalService, times(1)).findAllUsers();
    }

    @Test
    void getUserByIdTest() throws Exception {
        UUID userId = UUID.randomUUID();
        ViewUserDto userDto = new ViewUserDto(
                userId,
                null,
                Role.USER,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                "USA",
                "California",
                "Los Angeles",
                null
        );

        when(userExternalService.findUserById(userId)).thenReturn(userDto);

        mockMvcUserController.perform(get("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userExternalService, times(1)).findUserById(userId);
    }

    @Test
    void registerTest() throws Exception {
        RegistrationRequestDto request = RegistrationRequestDto.builder()
                .firstName("John")
                .lastName("Wick")
                .email("john@wick.com")
                .password("QwErTy123@")
                .phoneNumber("+420774285390")
                .country("Czech Republic")
                .region("Zlinsky")
                .city("Zlin")
                .build();

        String mockToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb21ldXNlcmlkIiwibmFtZSI6IkpvaG4gRG9lIn0.dummySignature";
        AuthenticationResponseDto responseDto = new AuthenticationResponseDto(mockToken);
        when(authenticationService.register(request)).thenReturn(responseDto);

        mockMvcAuthenticationController.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(mockToken));

        verify(authenticationService, times(1)).register(request);
    }
}
