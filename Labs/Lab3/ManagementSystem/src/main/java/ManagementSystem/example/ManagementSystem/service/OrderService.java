package ManagementSystem.example.ManagementSystem.service;

import ManagementSystem.example.ManagementSystem.event.DiscountEvent;
import ManagementSystem.example.ManagementSystem.model.Customer;
import ManagementSystem.example.ManagementSystem.model.Order;
import ManagementSystem.example.ManagementSystem.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final double LARGE_DISCOUNT_THRESHOLD = 80.0;
    
    @Autowired
    private DiscountService discountService;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    public Order processOrder(Long customerId, double amount) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        order.setCustomerId(customerId);
        order.setAmount(amount);
        
        double discount = discountService.calculateDiscount(customer, order);
        order.setDiscountAmount(discount);
        order.setFinalAmount(amount - discount);
        
        logger.info("Method: calculateDiscount, Customer: {}, Discount Amount: {}", 
                   customer.getName(), discount);
        
        if (discount > LARGE_DISCOUNT_THRESHOLD) {
            DiscountEvent event = new DiscountEvent(customer.getName(), discount, 
                                                  discountService.getClass().getSimpleName());
            eventPublisher.publishEvent(event);
        }
        
        return order;
    }
}
