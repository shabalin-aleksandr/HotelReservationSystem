package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.CreateUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.UserDto.ViewUserDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.UserExternalService;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserExternalService userExternalService;

    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userExternalService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create a sample CreateUserDto for the request
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setFirstName("John");
        createUserDto.setLastName("Wick");
        createUserDto.setEmail("john@wick.com");
        createUserDto.setPhoneNumber("+420774285390");
        createUserDto.setCountry("Czech Republic");
        createUserDto.setRegion("Zlinsky");
        createUserDto.setCity("Zlin");

        // Create a sample ViewUserDto for the response
        ViewUserDto viewUserDto = new ViewUserDto();
        viewUserDto.setUserId(UUID.randomUUID());
        createUserDto.setFirstName("John");
        createUserDto.setLastName("Wick");
        createUserDto.setEmail("john@wick.com");
        createUserDto.setPhoneNumber("+420774285390");
        createUserDto.setCountry("Czech Republic");
        createUserDto.setRegion("Zlinsky");
        createUserDto.setCity("Zlin");

        // Mock the behavior of the userExternalService
//        when(userExternalService.addUser(createUserDto)).thenReturn(viewUserDto);

        // Perform the POST request to create a user
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"firstName\": \"John\",\n" +
                                "  \"lastName\": \"Wick\",\n" +
                                "  \"email\": \"john@wick.com\",\n" +
                                "  \"phoneNumber\": \"+420774285390\",\n" +
                                "  \"country\": \"Czech Republic\",\n" +
                                "  \"region\": \"Zlinsky\",\n" +
                                "  \"city\": \"Zlin\"\n" +
                                "}"))
                .andExpect(status().isCreated()); // Expect HTTP 201 Created status
    }
}