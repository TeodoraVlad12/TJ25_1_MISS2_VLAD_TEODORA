package Students.example.Students.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "packs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pack {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(nullable = false)
    private Integer semester;
    
    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;
}
