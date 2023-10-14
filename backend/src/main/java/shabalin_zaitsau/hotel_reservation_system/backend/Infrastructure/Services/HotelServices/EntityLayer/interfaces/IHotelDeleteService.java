package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Services.HotelServices.EntityLayer.interfaces;

import java.util.UUID;

public interface IHotelDeleteService {
    void removeHotelById(UUID hotelId);
}
