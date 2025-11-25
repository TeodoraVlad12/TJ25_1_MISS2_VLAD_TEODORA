package Students.example.Students.service;

import Students.example.Students.dto.GradeEvent;
import Students.example.Students.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GradeDlqHandler {
    
    // @RabbitListener(queues = RabbitConfig.GRADE_DLQ_QUEUE)
    public void handleDlqMessage(GradeEvent gradeEvent) {
        System.err.println(" [" + java.time.LocalTime.now() + "] DLQ Handler - Failed grade after " + RabbitConfig.MAX_RETRY_ATTEMPTS + " retries: " + gradeEvent);
        System.err.println(" Manual intervention required for: studentCode=" + gradeEvent.getStudentCode() +
                          ", courseCode=" + gradeEvent.getCourseCode() + 
                          ", grade=" + gradeEvent.getGrade());

    }
}
