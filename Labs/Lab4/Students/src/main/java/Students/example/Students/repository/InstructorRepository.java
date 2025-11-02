package Students.example.Students.repository;

import Students.example.Students.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    
    Optional<Instructor> findByEmail(String email);
    
    List<Instructor> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT DISTINCT i FROM Instructor i JOIN FETCH i.courses WHERE SIZE(i.courses) > 0")
    List<Instructor> findInstructorsWithCourses();
    
    @Query("SELECT DISTINCT i FROM Instructor i JOIN i.courses c WHERE c.type = :courseType")
    List<Instructor> findInstructorsByCourseType(@Param("courseType") Students.example.Students.entity.Course.CourseType courseType);
}
