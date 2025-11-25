package QuickGrade.example.QuickGrade.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class GradeEvent implements Serializable {
    private String studentCode;
    private String courseCode;
    private Double grade;
}
