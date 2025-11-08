package Students.example.Students.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    
    @Column(unique = true, nullable = false)
    private String code;
    
    @Column(nullable = false)
    private Integer year;
    
    public Student(Long id, String name, String email, String username, String password, String code, Integer year) {
        super();
        setId(id);
        setName(name);
        setEmail(email);
        setUsername(username);
        setPassword(password);
        setRole(Role.STUDENT);
        setEnabled(true);
        this.code = code;
        this.year = year;
    }
}
