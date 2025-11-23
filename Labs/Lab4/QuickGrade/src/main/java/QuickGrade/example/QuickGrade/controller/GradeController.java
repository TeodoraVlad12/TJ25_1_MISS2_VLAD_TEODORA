package QuickGrade.example.QuickGrade.controller;

import QuickGrade.example.QuickGrade.dto.GradeEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<String> publishGrade(@RequestBody GradeEvent gradeEvent) {
        rabbitTemplate.convertAndSend("grade.exchange", "grade.new", gradeEvent);
        return ResponseEntity.ok("Grade published successfully");
    }
}
