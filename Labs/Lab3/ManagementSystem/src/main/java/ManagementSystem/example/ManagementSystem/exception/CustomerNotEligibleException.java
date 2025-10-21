package ManagementSystem.example.ManagementSystem.exception;

public class CustomerNotEligibleException extends RuntimeException {
    public CustomerNotEligibleException(String message) {
        super(message);
    }
}
