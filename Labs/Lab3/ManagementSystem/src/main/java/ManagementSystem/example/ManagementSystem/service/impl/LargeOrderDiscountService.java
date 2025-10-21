package ManagementSystem.example.ManagementSystem.service.impl;

import ManagementSystem.example.ManagementSystem.model.Customer;
import ManagementSystem.example.ManagementSystem.model.Order;
import ManagementSystem.example.ManagementSystem.service.DiscountService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("large-order")
public class LargeOrderDiscountService implements DiscountService {
    
    private static final double LARGE_ORDER_THRESHOLD = 1000.0;
    private static final double FIXED_DISCOUNT_AMOUNT = 100.0;
    
    @Override
    public double calculateDiscount(Customer customer, Order order) {
        if (order.getAmount() >= LARGE_ORDER_THRESHOLD) {
            return FIXED_DISCOUNT_AMOUNT;
        }
        return 0.0;
    }
}
