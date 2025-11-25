package Students.example.Students.controller;

import Students.example.Students.dto.GradeResponseDTO;
import Students.example.Students.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<GradeResponseDTO>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponseDTO>> getGradesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }
}
