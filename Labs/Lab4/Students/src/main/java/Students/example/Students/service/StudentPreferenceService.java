package Students.example.Students.service;

import Students.example.Students.dto.StudentPreferenceDTO;
import Students.example.Students.entity.Course;
import Students.example.Students.entity.Student;
import Students.example.Students.entity.StudentPreference;
import Students.example.Students.exception.PreferenceNotFoundException;
import Students.example.Students.repository.CourseRepository;
import Students.example.Students.repository.StudentPreferenceRepository;
import Students.example.Students.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentPreferenceService {
    
    private final StudentPreferenceRepository preferenceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    
    public StudentPreferenceService(StudentPreferenceRepository preferenceRepository,
                                   StudentRepository studentRepository,
                                   CourseRepository courseRepository) {
        this.preferenceRepository = preferenceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    
    public List<StudentPreferenceDTO> getAllPreferences() {
        return preferenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public StudentPreferenceDTO getPreferenceById(Long id) {
        StudentPreference preference = preferenceRepository.findById(id)
                .orElseThrow(() -> new PreferenceNotFoundException(id));
        return convertToDTO(preference);
    }
    
    public List<StudentPreferenceDTO> getPreferencesByStudentId(Long studentId) {
        return preferenceRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public StudentPreferenceDTO createPreference(StudentPreferenceDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        StudentPreference preference = new StudentPreference();
        preference.setStudent(student);
        preference.setCourse(course);
        preference.setPreferenceOrder(dto.getPreferenceOrder());
        
        StudentPreference saved = preferenceRepository.save(preference);
        return convertToDTO(saved);
    }
    
    @Transactional
    public StudentPreferenceDTO updatePreference(Long id, StudentPreferenceDTO dto) {
        StudentPreference preference = preferenceRepository.findById(id)
                .orElseThrow(() -> new PreferenceNotFoundException(id));
        
        if (dto.getStudentId() != null) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            preference.setStudent(student);
        }
        
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            preference.setCourse(course);
        }
        
        if (dto.getPreferenceOrder() != null) {
            preference.setPreferenceOrder(dto.getPreferenceOrder());
        }
        
        StudentPreference updated = preferenceRepository.save(preference);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void deletePreference(Long id) {
        if (!preferenceRepository.existsById(id)) {
            throw new PreferenceNotFoundException(id);
        }
        preferenceRepository.deleteById(id);
    }
    
    private StudentPreferenceDTO convertToDTO(StudentPreference preference) {
        StudentPreferenceDTO dto = new StudentPreferenceDTO();
        dto.setId(preference.getId());
        dto.setStudentId(preference.getStudent().getId());
        dto.setCourseId(preference.getCourse().getId());
        dto.setPreferenceOrder(preference.getPreferenceOrder());
        dto.setStudentName(preference.getStudent().getName());
        dto.setCourseName(preference.getCourse().getName());
        return dto;
    }
}
