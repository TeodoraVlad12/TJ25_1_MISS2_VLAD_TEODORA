package Students.example.Students.service;

import Students.example.Students.entity.Grade;
import Students.example.Students.entity.Student;
import Students.example.Students.entity.Course;
import Students.example.Students.dto.GradeResponseDTO;
import Students.example.Students.repository.GradeRepository;
import Students.example.Students.repository.StudentRepository;
import Students.example.Students.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeService {
    
    @Autowired
    private GradeRepository gradeRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    public Grade saveGrade(String studentCode, String courseCode, Double gradeValue) {
        Optional<Student> student = studentRepository.findByCode(studentCode);
        Optional<Course> course = courseRepository.findByCode(courseCode);
        
        if (student.isEmpty()) {
            throw new RuntimeException("Student not found with code: " + studentCode);
        }
        
        if (course.isEmpty()) {
            throw new RuntimeException("Course not found with code: " + courseCode);
        }
        
        if (course.get().getType() != Course.CourseType.COMPULSORY) {
            System.out.println("Skipping grade for optional course: " + courseCode);
            return null;
        }
        
        Grade grade = new Grade(student.get(), course.get(), gradeValue);
        return gradeRepository.save(grade);
    }
    
    public List<GradeResponseDTO> getAllGrades() {
        return gradeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<GradeResponseDTO> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private GradeResponseDTO convertToDTO(Grade grade) {
        return new GradeResponseDTO(
                grade.getId(),
                grade.getStudent().getId(),
                grade.getStudent().getCode(),
                grade.getCourse().getId(),
                grade.getCourse().getCode(),
                grade.getCourse().getName(),
                grade.getValue(),
                grade.getRecordedAt()
        );
    }
}
