package Students.example.Students.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponseDTO {
    private Long id;
    private Long studentId;
    private String studentCode;
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Double value;
    private LocalDateTime recordedAt;
}
