package Students.example.Students.repository;

import Students.example.Students.entity.Course;
import Students.example.Students.entity.Course.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Optional<Course> findByCode(String code);
    
    List<Course> findByType(CourseType type);
    
    List<Course> findByInstructorId(Long instructorId);
    
    List<Course> findByPackId(Long packId);
    
    @Query("SELECT c FROM Course c JOIN c.instructor i WHERE i.name LIKE %:instructorName%")
    List<Course> findCoursesByInstructorName(@Param("instructorName") String instructorName);
    
    @Query("SELECT c FROM Course c JOIN c.pack p WHERE p.year = :year AND p.semester = :semester")
    List<Course> findCoursesByPackYearAndSemester(@Param("year") Integer year, @Param("semester") Integer semester);
    
    @Query("SELECT c FROM Course c WHERE c.groupCount > :minGroupCount")
    List<Course> findCoursesWithGroupCountGreaterThan(@Param("minGroupCount") Integer minGroupCount);
    
    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.groupCount = :newGroupCount WHERE c.type = :courseType")
    int updateGroupCountByCourseType(@Param("newGroupCount") Integer newGroupCount, @Param("courseType") CourseType courseType);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Course c WHERE c.pack.id = :packId")
    int deleteCoursesByPackId(@Param("packId") Long packId);
}
