package Students.example.Students.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String GRADE_QUEUE = "grade.queue";
    public static final String GRADE_DLQ_QUEUE = "grade.dlq.queue";
    public static final String GRADE_EXCHANGE = "grade.exchange";
    public static final String GRADE_DLQ_EXCHANGE = "grade.dlq.exchange";
    public static final int MAX_RETRY_ATTEMPTS = 3;
    public static final int RETRY_DELAY_MS = 2000; // 2 sec

    @Bean
    public Queue gradeQueue() {
        return new Queue(GRADE_QUEUE, true, false, false, Map.of(
            "x-dead-letter-exchange", GRADE_DLQ_EXCHANGE,
            "x-dead-letter-routing-key", "grade.dlq"
        ));
    }
    
    @Bean
    public Queue gradeDlqQueue() {
        return new Queue(GRADE_DLQ_QUEUE);
    }
    
    @Bean
    public TopicExchange gradeExchange() {
        return new TopicExchange(GRADE_EXCHANGE);
    }
    
    @Bean
    public TopicExchange gradeDlqExchange() {
        return new TopicExchange(GRADE_DLQ_EXCHANGE);
    }
    
    @Bean
    public Binding gradeBinding() {
        return BindingBuilder.bind(gradeQueue()).to(gradeExchange()).with("grade.new");
    }
    
    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(gradeDlqQueue()).to(gradeDlqExchange()).with("grade.dlq");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());

        RetryTemplate retryTemplate = new RetryTemplate();
        
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(MAX_RETRY_ATTEMPTS);
        retryTemplate.setRetryPolicy(retryPolicy);
        
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(RETRY_DELAY_MS);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        
        factory.setRetryTemplate(retryTemplate);
        factory.setDefaultRequeueRejected(false);
        
        return factory;
    }
}
