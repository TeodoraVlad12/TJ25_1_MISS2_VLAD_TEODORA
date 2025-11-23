package Students.example.Students.dto;

import lombok.Data;

@Data
public class GradeEvent {
    private String studentCode;
    private String courseCode;
    private Double grade;
}