package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Use this link to access the Swagger UI:
 * localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
                .components(new Components().addSecuritySchemes
                        ("bearerAuth", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private Info apiInfo() {
        return new Info()
                .title("Hotel Reservation System")
                .description("""
                        The Hotel Reservation System API provides a set of endpoints
                        for managing hotel reservations. It allows users to perform various
                        actions related to hotel bookings, rooms, and reservations.

                        Contacts:
                        - Aleksandr Shabalin: a_shabalin@utb.cz
                        - Ivan Zaitsau: i_zaitsau@utb.cz""")
                .version("1.0.0");
    }
}