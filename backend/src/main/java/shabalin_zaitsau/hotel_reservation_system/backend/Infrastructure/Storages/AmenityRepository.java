package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Amenity;
import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.Room;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, UUID> {
    List<Amenity> findByRoom_RoomId(UUID roomId);
    Optional<Amenity> findByRoom_RoomIdAndAmenityId(UUID roomId,UUID amenityId);

    Optional<Amenity> findByRoomAndAmenityName(Room room, String amenityName);


}
