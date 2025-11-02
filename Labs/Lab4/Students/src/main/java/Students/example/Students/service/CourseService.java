package Students.example.Students.service;

import Students.example.Students.entity.Course;
import Students.example.Students.entity.Course.CourseType;
import Students.example.Students.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    public Optional<Course> getCourseByCode(String code) {
        return courseRepository.findByCode(code);
    }
    
    public List<Course> getCoursesByType(CourseType type) {
        return courseRepository.findByType(type);
    }
    
    public List<Course> getCoursesByInstructorId(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
    
    public List<Course> getCoursesByPackId(Long packId) {
        return courseRepository.findByPackId(packId);
    }
    
    public List<Course> getCoursesByInstructorName(String instructorName) {
        return courseRepository.findCoursesByInstructorName(instructorName);
    }
    
    public List<Course> getCoursesByPackYearAndSemester(Integer year, Integer semester) {
        return courseRepository.findCoursesByPackYearAndSemester(year, semester);
    }
    
    public List<Course> getCoursesWithGroupCountGreaterThan(Integer minGroupCount) {
        return courseRepository.findCoursesWithGroupCountGreaterThan(minGroupCount);
    }
    
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }
    
    public long countCourses() {
        return courseRepository.count();
    }
    
    public int updateGroupCountByCourseType(Integer newGroupCount, CourseType courseType) {
        return courseRepository.updateGroupCountByCourseType(newGroupCount, courseType);
    }
    
    public int deleteCoursesByPackId(Long packId) {
        return courseRepository.deleteCoursesByPackId(packId);
    }
}
