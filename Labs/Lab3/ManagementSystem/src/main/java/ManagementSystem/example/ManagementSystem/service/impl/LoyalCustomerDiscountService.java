package ManagementSystem.example.ManagementSystem.service.impl;

import ManagementSystem.example.ManagementSystem.model.Customer;
import ManagementSystem.example.ManagementSystem.model.Order;
import ManagementSystem.example.ManagementSystem.service.DiscountService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("loyal")
public class LoyalCustomerDiscountService implements DiscountService {
    
    private static final double LOYALTY_DISCOUNT_PERCENTAGE = 0.10;
    
    @Override
    public double calculateDiscount(Customer customer, Order order) {
        if (customer.isLoyal()) {
            return order.getAmount() * LOYALTY_DISCOUNT_PERCENTAGE;
        }
        return 0.0;
    }
}
