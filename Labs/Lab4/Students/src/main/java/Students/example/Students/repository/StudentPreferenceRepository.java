package Students.example.Students.repository;

import Students.example.Students.entity.StudentPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPreferenceRepository extends JpaRepository<StudentPreference, Long> {
    
    List<StudentPreference> findByStudentId(Long studentId);
    
    List<StudentPreference> findByCourseId(Long courseId);
    
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}
