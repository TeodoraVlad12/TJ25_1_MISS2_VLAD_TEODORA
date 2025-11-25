package QuickGrade.example.QuickGrade.repository;

import QuickGrade.example.QuickGrade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentCode(String studentCode);
    List<Grade> findByCourseCode(String courseCode);
}
