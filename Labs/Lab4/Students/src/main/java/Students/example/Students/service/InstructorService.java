package Students.example.Students.service;

import Students.example.Students.entity.Instructor;
import Students.example.Students.entity.Course.CourseType;
import Students.example.Students.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    
    @Autowired
    private InstructorRepository instructorRepository;
    
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }
    
    public Optional<Instructor> getInstructorById(Long id) {
        return instructorRepository.findById(id);
    }
    
    public Optional<Instructor> getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email);
    }
    
    public List<Instructor> getInstructorsByNameContaining(String name) {
        return instructorRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Instructor> getInstructorsWithCourses() {
        return instructorRepository.findInstructorsWithCourses();
    }
    
    public List<Instructor> getInstructorsByCourseType(CourseType courseType) {
        return instructorRepository.findInstructorsByCourseType(courseType);
    }
    
    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }
    
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return instructorRepository.existsById(id);
    }
    
    public long countInstructors() {
        return instructorRepository.count();
    }
}
