package shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "images")
public class Image {

    @Id
    @Column(name = "image_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String contentType;
    private Long size;
    @Lob
    private byte[] data;
}
