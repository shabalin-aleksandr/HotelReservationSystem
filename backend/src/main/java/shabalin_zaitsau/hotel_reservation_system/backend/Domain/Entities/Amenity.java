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
@Table(name = "amenity")
public class Amenity {

    @Id
    @Column(name = "amenity_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID amenityId;

    @NotBlank
    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description", nullable = false)
    public String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
