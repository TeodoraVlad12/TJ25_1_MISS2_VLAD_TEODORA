package QuickGrade.example.QuickGrade.controller;

import QuickGrade.example.QuickGrade.dto.GradeEvent;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<String> publishGrade(@RequestBody GradeEvent gradeEvent) {
        System.out.println("QuickGrade received grade: " + gradeEvent);
        rabbitTemplate.convertAndSend("grade.exchange", "grade.new", gradeEvent);
        System.out.println("QuickGrade published to queue: " + gradeEvent);
        return ResponseEntity.ok("Grade published successfully");
    }
    
    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadGrades(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("QuickGrade received CSV file: " + file.getOriginalFilename() + " (size: " + file.getSize() + " bytes)");
            List<GradeEvent> publishedGrades = new ArrayList<>();
            
            try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream())).withSkipLines(1).build()) {
                String[] line;
                int rowNumber = 1;
                while ((line = reader.readNext()) != null) {
                    if (line.length >= 3) {
                        String studentCode = line[0].trim();
                        String courseCode = line[1].trim();
                        Double grade = Double.parseDouble(line[2].trim());
                        
                        GradeEvent gradeEvent = new GradeEvent();
                        gradeEvent.setStudentCode(studentCode);
                        gradeEvent.setCourseCode(courseCode);
                        gradeEvent.setGrade(grade);
                        
                        System.out.println("QuickGrade processing CSV row " + rowNumber + ": " + gradeEvent);
                        rabbitTemplate.convertAndSend("grade.exchange", "grade.new", gradeEvent);
                        System.out.println("QuickGrade published CSV row " + rowNumber + " to queue: " + gradeEvent);
                        publishedGrades.add(gradeEvent);
                    }
                    rowNumber++;
                }
            }
            
            System.out.println("QuickGrade finished processing CSV - published " + publishedGrades.size() + " grades total");
            return ResponseEntity.ok("Successfully published " + publishedGrades.size() + " grades to queue");
        } catch (Exception e) {
            System.err.println("QuickGrade CSV processing error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to process CSV file: " + e.getMessage());
        }
    }
}
