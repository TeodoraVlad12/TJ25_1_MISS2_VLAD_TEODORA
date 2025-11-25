package QuickGrade.example.QuickGrade.service;

import QuickGrade.example.QuickGrade.entity.Grade;
import QuickGrade.example.QuickGrade.repository.GradeRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class GradeService {
    
    @Autowired
    private GradeRepository gradeRepository;
    
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }
    
    public List<Grade> getGradesByStudent(String studentCode) {
        return gradeRepository.findByStudentCode(studentCode);
    }
    
    public List<Grade> getGradesByCourse(String courseCode) {
        return gradeRepository.findByCourseCode(courseCode);
    }
    
    public Grade saveGrade(String studentCode, String courseCode, Double value) {
        Grade grade = new Grade(studentCode, courseCode, value);
        return gradeRepository.save(grade);
    }
    
    public List<Grade> uploadGradesFromCsv(MultipartFile file) {
        List<Grade> grades = new ArrayList<>();
        
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream())).withSkipLines(1).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length >= 3) {
                    String studentCode = line[0].trim();
                    String courseCode = line[1].trim();
                    Double value = Double.parseDouble(line[2].trim());
                    
                    Grade grade = new Grade(studentCode, courseCode, value);
                    grades.add(gradeRepository.save(grade));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file", e);
        }
        
        return grades;
    }
}
