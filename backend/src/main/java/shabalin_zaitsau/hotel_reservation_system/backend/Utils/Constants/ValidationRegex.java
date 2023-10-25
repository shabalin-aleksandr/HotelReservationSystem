package shabalin_zaitsau.hotel_reservation_system.backend.Utils.Constants;

/**
 * Utility class containing regular expressions for common validation tasks.
 */
public class ValidationRegex {

    /**
     * Regular expression pattern for validating email addresses.
     * This pattern checks for standard email format.
     */
    public static final String EMAIL_REGEX = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /**
     * Regular expression pattern for validating passwords.
     * This pattern enforces strong password requirements:
     * - At least one digit
     * - At least one lowercase letter
     * - At least one uppercase letter
     * - At least one special character from @#$%^&+=
     * - Minimum length of 8 characters
     * - No whitespace allowed
     */
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
}

