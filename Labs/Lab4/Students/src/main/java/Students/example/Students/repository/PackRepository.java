package Students.example.Students.repository;

import Students.example.Students.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    
    List<Pack> findByYear(Integer year);
    
    List<Pack> findByYearAndSemester(Integer year, Integer semester);
    
    List<Pack> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT DISTINCT p FROM Pack p JOIN FETCH p.courses WHERE SIZE(p.courses) > 0")
    List<Pack> findPacksWithCourses();
    
    @Query("SELECT p, SIZE(p.courses) FROM Pack p WHERE p.id = :packId")
    Object[] findPackWithCourseCount(@Param("packId") Long packId);
}
