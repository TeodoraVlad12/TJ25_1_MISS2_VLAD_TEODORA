package Students.example.Students.repository;

import Students.example.Students.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByCode(String code);
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByYear(Integer year);
}
