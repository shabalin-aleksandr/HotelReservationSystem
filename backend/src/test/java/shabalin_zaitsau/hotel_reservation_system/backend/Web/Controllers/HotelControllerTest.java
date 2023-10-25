package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.CreateHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.HotelDto.ViewHotelDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.HotelExternalService;

import java.util.UUID;

public class HotelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HotelExternalService hotelExternalService;

    private HotelController hotelController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        hotelController = new HotelController(hotelExternalService);
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create a sample CreateHotelDto for the request
        CreateHotelDto createHotelDto = new CreateHotelDto();
        createHotelDto.setHotelName("Grand Hotel");
        createHotelDto.setCountry("Czech Republic");
        createHotelDto.setCity("Brno");
        createHotelDto.setAddress("Benesova 19");
        createHotelDto.setReceptionNumber("574-435-254");

        // Create a sample ViewHotelDto for the response
        ViewHotelDto viewHotelDto = new ViewHotelDto();
        viewHotelDto.setHotelId(UUID.randomUUID());
        viewHotelDto.setHotelName("Grand Hotel");
        viewHotelDto.setCountry("Czech Republic");
        viewHotelDto.setCity("Brno");
        viewHotelDto.setAddress("Benesova 19");
        viewHotelDto.setReceptionNumber("574-435-254");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/hotels/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hotelName\":\"Grand Hotel\",\"country\":\"Czech Republic\",\"city\":\"Brno\",\"address\":\"Benesova 19\",\"receptionNumber\":\"574-435-254\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
}
