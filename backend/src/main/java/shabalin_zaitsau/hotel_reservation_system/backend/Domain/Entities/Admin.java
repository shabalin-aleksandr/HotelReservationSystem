package shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Admins")
public class Admin {

    @Id
    @Column(name = "admin_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID adminId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User userDetails;

    @NotBlank
    @Column(name = "admin_type")
    @Enumerated(EnumType.STRING)
    private AdminType adminType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "managedBy")
    @ToString.Exclude
    private Set<Hotel> hotels;
}
