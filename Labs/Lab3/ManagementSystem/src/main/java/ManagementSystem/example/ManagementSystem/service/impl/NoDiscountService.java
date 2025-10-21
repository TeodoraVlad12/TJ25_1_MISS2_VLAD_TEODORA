package ManagementSystem.example.ManagementSystem.service.impl;

import ManagementSystem.example.ManagementSystem.model.Customer;
import ManagementSystem.example.ManagementSystem.model.Order;
import ManagementSystem.example.ManagementSystem.service.DiscountService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("no-discount")
public class NoDiscountService implements DiscountService {
    
    @Override
    public double calculateDiscount(Customer customer, Order order) {
        return 0.0;
    }
}
