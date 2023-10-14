package shabalin_zaitsau.hotel_reservation_system.backend.Infrastructure.Dto.RoomDto.interfaces;

import shabalin_zaitsau.hotel_reservation_system.backend.Domain.Entities.enums.CategoryType;

public interface IRoomCreate {
    String getRoomNumber();
    CategoryType getCategory();
    Double getPricePerNight();
}
