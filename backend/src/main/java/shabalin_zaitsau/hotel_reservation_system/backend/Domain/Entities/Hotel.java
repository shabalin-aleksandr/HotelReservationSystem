package shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Hotels")
public class Hotel {

    @Id
    @Column(name = "hotel_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hotelId;

    @NotBlank
    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @NotBlank
    @Column(name = "country", nullable = false)
    private String country;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Column(name = "reception_number", nullable = false)
    private String receptionNumber;

    @Min(0)
    @Max(5)
    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "rating_count", nullable = false)
    private int count;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "hotel")
    @JsonManagedReference
    private Set<Room> availableRooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by_admin_id")
    @ToString.Exclude
    private Admin managedBy;

    public void calculateRating(double newRating) {
        double result = count * rating;
        count++;
        rating = (result + newRating) / count;
    }
}
