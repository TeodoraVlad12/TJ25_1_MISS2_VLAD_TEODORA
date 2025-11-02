package Students.example.Students.service;

import Students.example.Students.entity.Student;
import Students.example.Students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Optional<Student> getStudentByCode(String code) {
        return studentRepository.findByCode(code);
    }
    
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    
    public List<Student> getStudentsByYear(Integer year) {
        return studentRepository.findByYear(year);
    }
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }
    
    public long countStudents() {
        return studentRepository.count();
    }
}
