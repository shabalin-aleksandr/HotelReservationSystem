package shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rooms")
public class Room {

    @Id
    @Column(name = "room_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    @NotBlank
    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @NotBlank
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "room")
    private Set<Amenity> availableAmenities;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "reservedRoom")
    private Set<Reservation> reservations;

    @NotBlank
    @Column(name = "price_per_night", nullable = false)
    private Double pricePerNight;
}
