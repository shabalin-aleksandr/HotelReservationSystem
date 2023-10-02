package shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @Column(name = "hotel_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hotelId;

    @NotBlank
    @Column(name = "hotel_name", nullable = false)
    public String hotelName;

    @NotBlank
    @Column(name = "country", nullable = false)
    public String country;

    @NotBlank
    @Column(name = "city", nullable = false)
    public String city;

    @NotBlank
    @Column(name = "address", nullable = false)
    public String address;

    @NotBlank
    @Column(name = "reception_number", nullable = false)
    private String receptionNumber;
}
