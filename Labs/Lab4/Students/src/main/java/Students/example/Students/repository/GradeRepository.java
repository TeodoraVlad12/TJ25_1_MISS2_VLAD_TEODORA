package Students.example.Students.repository;

import Students.example.Students.entity.Grade;
import Students.example.Students.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudent(Student student);
    List<Grade> findByStudentId(Long studentId);
}
