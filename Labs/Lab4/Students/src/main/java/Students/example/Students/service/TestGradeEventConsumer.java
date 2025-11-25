package Students.example.Students.service;

import Students.example.Students.dto.GradeEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestGradeEventConsumer {
    
    @Autowired
    private GradeService gradeService;
    
    @RabbitListener(queues = "grade.queue")
    public void handleGradeEvent(GradeEvent gradeEvent) {
        try {
            System.out.println(" [" + java.time.LocalTime.now() + "] Processing grade: " + gradeEvent);
            gradeService.saveGrade(gradeEvent.getStudentCode(), gradeEvent.getCourseCode(), gradeEvent.getGrade());
            System.out.println(" [" + java.time.LocalTime.now() + "] Successfully processed grade: " + gradeEvent);
        } catch (Exception e) {
            System.err.println("[" + java.time.LocalTime.now() + "] Failed to process grade: " + gradeEvent + " - Error: " + e.getMessage());
            System.err.println(" Spring AMQP will handle retry automatically...");
            throw new RuntimeException("Grade processing failed: " + e.getMessage(), e);
        }
    }
}
