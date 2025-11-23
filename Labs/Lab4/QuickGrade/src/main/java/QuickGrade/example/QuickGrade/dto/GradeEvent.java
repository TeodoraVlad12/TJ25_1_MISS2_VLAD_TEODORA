package QuickGrade.example.QuickGrade.dto;

import lombok.Data;

@Data
public class GradeEvent {
    private String studentCode;
    private String courseCode;
    private Double grade;
}
