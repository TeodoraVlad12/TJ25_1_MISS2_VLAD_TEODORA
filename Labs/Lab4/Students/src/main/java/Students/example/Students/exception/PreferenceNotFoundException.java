package Students.example.Students.exception;

public class PreferenceNotFoundException extends RuntimeException {
    
    public PreferenceNotFoundException(Long id) {
        super("Student preference not found with id: " + id);
    }
    
    public PreferenceNotFoundException(String message) {
        super(message);
    }
}
