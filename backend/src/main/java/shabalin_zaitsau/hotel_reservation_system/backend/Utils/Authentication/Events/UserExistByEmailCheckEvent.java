package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserExistByEmailCheckEvent extends ApplicationEvent {

    private final String email;
    private boolean userExists;
    private UserDetails userDetails;

    public UserExistByEmailCheckEvent(String email) {
        super(email);
        this.email = email;
    }
}
