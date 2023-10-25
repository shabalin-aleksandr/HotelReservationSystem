package shabalin_zaitsau.hotel_reservation_system.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("\uD83D\uDE80 Application is running on: http://localhost:8080/");
		System.out.println("\uD83C\uDF10 Swagger UI: http://localhost:8080/swagger-ui/index.html");
	}
}
