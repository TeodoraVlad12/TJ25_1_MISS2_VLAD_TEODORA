package ManagementSystem.example.ManagementSystem.listener;

import ManagementSystem.example.ManagementSystem.event.DiscountEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DiscountEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(DiscountEventListener.class);
    
    @EventListener
    public void handleDiscountEvent(DiscountEvent event) {
        logger.info("Large discount applied! Customer: {}, Amount: {}, Type: {}", 
                   event.getCustomerName(), event.getDiscountAmount(), event.getDiscountType());
    }
}
