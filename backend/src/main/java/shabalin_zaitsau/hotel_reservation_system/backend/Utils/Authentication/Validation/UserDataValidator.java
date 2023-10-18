package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Authentication.Validation;

import org.springframework.stereotype.Component;
import shabalin_zaitsau.hotel_reservation_system.backend.Utils.Constants.ValidationRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class that provides methods to validate user input, such as email addresses and passwords.
 */
@Component
public class UserDataValidator {

    /**
     * Checks if the given email address is valid.
     *
     * @param email the email address to check
     * @return {@code true} if the email address is valid, {@code false} otherwise
     */
    public boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        Pattern pattern = Pattern.compile(ValidationRegex.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    /**
     * Checks if the given password is valid.
     *
     * @param password the password to check
     * @return {@code true} if the password is valid, {@code false} otherwise
     */
    public boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(ValidationRegex.PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }
}
