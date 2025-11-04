package Students.example.Students.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "studentPreference")
public class StudentPreferenceDTO {
    
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Positive(message = "Student ID must be positive")
    private Long studentId;
    
    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be positive")
    private Long courseId;
    
    @NotNull(message = "Preference order is required")
    @Positive(message = "Preference order must be positive")
    private Integer preferenceOrder;
    
    private String studentName;
    private String courseName;
}
