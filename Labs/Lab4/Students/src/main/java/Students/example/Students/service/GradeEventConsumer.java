package Students.example.Students.service;

import Students.example.Students.dto.GradeEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GradeEventConsumer {
    
    @RabbitListener(queues = "grade.queue")
    public void handleGradeEvent(GradeEvent gradeEvent) {
        System.out.println("Received grade: " + gradeEvent);
    }
}