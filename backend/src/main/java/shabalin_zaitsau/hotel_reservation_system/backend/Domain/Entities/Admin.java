package shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.AdminType;

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

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotBlank
    @Column(name = "admin_type")
    @Enumerated(EnumType.STRING)
    private AdminType adminType;
}
