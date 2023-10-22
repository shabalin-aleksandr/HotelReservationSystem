package shabalin_zaitsau.hotel_reservation_system.backend.Web.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.CreateRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.ViewRoomDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.RoomExternalService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomExternalService roomExternalService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID hotelId;
    private CreateRoomDto createRoomDto;
    private ViewRoomDto expectedRoom;

    @BeforeEach
    void setUp() {
        hotelId = UUID.randomUUID();

        // Prepare the createRoomDto
        createRoomDto = new CreateRoomDto();
        createRoomDto.setRoomNumber("104");
        createRoomDto.setCategory(CategoryType.DELUXE);
        createRoomDto.setPricePerNight(14000.0);

        // Prepare the expected room
        expectedRoom = new ViewRoomDto();
        expectedRoom.setRoomId(UUID.randomUUID());
        expectedRoom.setHotelId(hotelId);
        expectedRoom.setRoomNumber("104");
        expectedRoom.setCategory(CategoryType.DELUXE);
        expectedRoom.setPricePerNight(14000.0);

        when(roomExternalService.addRoom(eq(hotelId), Mockito.any(CreateRoomDto.class)))
                .thenReturn(expectedRoom);
    }

    @Test
    void testCreateRoom() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/rooms/create/{hotelId}", hotelId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRoomDto))
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomId").value(expectedRoom.getRoomId().toString()))
                .andExpect(jsonPath("$.hotelId").value(expectedRoom.getHotelId().toString()))
                .andExpect(jsonPath("$.roomNumber").value(expectedRoom.getRoomNumber()))
                .andExpect(jsonPath("$.category").value(expectedRoom.getCategory().name()))
                .andExpect(jsonPath("$.pricePerNight").value(expectedRoom.getPricePerNight()));
    }
}
