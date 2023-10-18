package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserAuthenticationCheckEvent extends ApplicationEvent {

    private final String email;
    private String password;
    private UserDetails userDetails;

    public UserAuthenticationCheckEvent(String email, String password) {
        super(email);
        this.email = email;
        this.password = password;
    }
}
