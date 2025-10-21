package ManagementSystem.example.ManagementSystem.service;

import ManagementSystem.example.ManagementSystem.model.Customer;
import ManagementSystem.example.ManagementSystem.model.Order;

public interface DiscountService {
    double calculateDiscount(Customer customer, Order order);
}
