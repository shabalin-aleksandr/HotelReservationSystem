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
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.CreateReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.ReservationDto.ViewReservationDto;
import shabalin_zaitsau.hotel_reservation_system.backend.Web.ExternalServices.ReservationExternalService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationExternalService reservationExternalService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID hotelId;
    private UUID roomId;
    private UUID userId;
    private CreateReservationDto createReservationDto;
    private ViewReservationDto expectedReservation;

    @BeforeEach
    void setUp() {
        hotelId = UUID.randomUUID();
        roomId = UUID.randomUUID();
        userId = UUID.randomUUID();

        // Prepare the createReservationDto
        createReservationDto = new CreateReservationDto();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date reservationFrom = null;
        Date reservationTo = null;
        try {
            reservationFrom = dateFormat.parse("16/10/2023");
            reservationTo = dateFormat.parse("21/10/2023");
        } catch (Exception e) {
            e.printStackTrace();
        }
        createReservationDto.setReservationFrom(reservationFrom);
        createReservationDto.setReservationTo(reservationTo);

        // Prepare the expected reservation
        expectedReservation = new ViewReservationDto();
        expectedReservation.setReservationId(UUID.randomUUID());
        expectedReservation.setUserId(userId);
        expectedReservation.setRoomId(roomId);
        expectedReservation.setReservationFrom(reservationFrom);
        expectedReservation.setReservationTo(reservationTo);
        expectedReservation.setTotalDays(5);
        expectedReservation.setTotalPrice(4765.80);

        when(reservationExternalService.addReservation(eq(hotelId), eq(roomId), Mockito.any(CreateReservationDto.class)))
                .thenReturn(expectedReservation);
    }

    @Test
    void testAddReservationToRoomForUser() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/reservations/{hotelId}/rooms/{roomId}/users/{userId}", hotelId, roomId, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createReservationDto))
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.reservationId").value(expectedReservation.getReservationId().toString()))
                .andExpect(jsonPath("$.userId").value(expectedReservation.getUserId().toString()))
                .andExpect(jsonPath("$.roomId").value(expectedReservation.getRoomId().toString()))
                .andExpect(jsonPath("$.reservationFrom").isString()) // Check that it's a string
                .andExpect(jsonPath("$.reservationTo").isString()) // Check that it's a string
                .andExpect(jsonPath("$.totalDays").value(expectedReservation.getTotalDays()))
                .andExpect(jsonPath("$.totalPrice").value(expectedReservation.getTotalPrice()));
    }
}
