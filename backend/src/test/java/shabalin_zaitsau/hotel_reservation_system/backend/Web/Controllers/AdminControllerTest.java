package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.CreateAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.AdminDto.ViewAdminDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.AdminExternalService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminExternalService adminExternalService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private CreateAdminDto createAdminDto;
    private ViewAdminDto expectedAdmin;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        // Prepare the createAdminDto
        createAdminDto = new CreateAdminDto();
        createAdminDto.setAdminType(AdminType.SUPER_ADMIN);

        // Prepare the expected admin
        expectedAdmin = new ViewAdminDto();
        expectedAdmin.setAdminId(UUID.randomUUID());
        expectedAdmin.setAdminType(AdminType.SUPER_ADMIN);

        // Mock the behavior of adminExternalService to return the expectedAdmin
        when(adminExternalService.addAdmin(eq(userId), ArgumentMatchers.any(CreateAdminDto.class)))
                .thenReturn(expectedAdmin);
    }

    @Test
    void testCreateAdmin() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/admins/create/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAdminDto))
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.adminId").value(expectedAdmin.getAdminId().toString()))
                .andExpect(jsonPath("$.adminType").value(expectedAdmin.getAdminType().name()));
    }
}

