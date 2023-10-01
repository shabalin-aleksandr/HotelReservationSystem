package shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "reservation_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reservationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // TODO: change to OneToOne annotation, when Room entity is implemented
    @Column(name = "room_id")
    private UUID roomId;

    @Column(name = "reservation_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationFrom;

    @Column(name = "reservation_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationTo;

    @Column(name = "total_price")
    private double totalPrice;
}
