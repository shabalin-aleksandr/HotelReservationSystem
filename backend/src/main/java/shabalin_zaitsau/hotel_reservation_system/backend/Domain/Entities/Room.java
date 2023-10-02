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
@Table(name = "room")

public class Room {

    @Id
    @Column(name = "room_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @NotBlank
    @Column(name = "room_number", nullable = false)
    public String roomNumber;

    @NotBlank
    @Column(name = "category", nullable = false)
    public String category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resrvation_id", nullable = false)
    private Reservation reservation;

    @NotBlank
    @Column(name = "price_per_night", nullable = false)
    public double pricePerNight;

}
