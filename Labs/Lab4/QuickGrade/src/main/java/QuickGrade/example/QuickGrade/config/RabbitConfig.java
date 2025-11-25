package QuickGrade.example.QuickGrade.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String GRADE_EXCHANGE = "grade.exchange";
    public static final String GRADE_QUEUE = "grade.queue";
    public static final String GRADE_ROUTING_KEY = "grade.new";
    public static final String DLQ_EXCHANGE = "grade.dlq.exchange";
    public static final String DLQ_QUEUE = "grade.dlq.queue";

    @Bean
    public TopicExchange gradeExchange() {
        return new TopicExchange(GRADE_EXCHANGE);
    }

    @Bean
    public TopicExchange dlqExchange() {
        return new TopicExchange(DLQ_EXCHANGE);
    }

    @Bean
    public Queue gradeQueue() {
        return QueueBuilder.durable(GRADE_QUEUE)
                .withArgument("x-dead-letter-exchange", DLQ_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "grade.dlq")
                .build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    @Bean
    public Binding gradeBinding() {
        return BindingBuilder.bind(gradeQueue()).to(gradeExchange()).with(GRADE_ROUTING_KEY);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlqQueue()).to(dlqExchange()).with("grade.dlq");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
