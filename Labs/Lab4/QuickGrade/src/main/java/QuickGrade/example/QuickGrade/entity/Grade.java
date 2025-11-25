package QuickGrade.example.QuickGrade.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "student_code", nullable = false)
    private String studentCode;
    
    @Column(name = "course_code", nullable = false)
    private String courseCode;
    
    @Column(name = "grade_value", nullable = false)
    private Double value;
    
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;
    
    public Grade(String studentCode, String courseCode, Double value) {
        this.studentCode = studentCode;
        this.courseCode = courseCode;
        this.value = value;
        this.recordedAt = LocalDateTime.now();
    }
}
