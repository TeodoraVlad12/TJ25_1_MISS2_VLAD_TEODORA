package Students.example.Students.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseType type;
    
    @Column(unique = true, nullable = false)
    private String code;
    
    @Column(nullable = false)
    private String abbr;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pack_id")
    private Pack pack;
    
    @Column(name = "group_count", nullable = false)
    private Integer groupCount = 1;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    public enum CourseType {
        COMPULSORY, OPTIONAL
    }
}
