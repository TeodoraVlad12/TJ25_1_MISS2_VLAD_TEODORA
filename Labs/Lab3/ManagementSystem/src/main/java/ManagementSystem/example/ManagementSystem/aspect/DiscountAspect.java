package ManagementSystem.example.ManagementSystem.aspect;

import ManagementSystem.example.ManagementSystem.exception.CustomerNotEligibleException;
import ManagementSystem.example.ManagementSystem.model.Customer;
import ManagementSystem.example.ManagementSystem.repository.CustomerRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DiscountAspect {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Before("execution(* ManagementSystem.example.ManagementSystem.service.OrderService.processOrder(..))")
    public void validateCustomerEligibility(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long) {
            Long customerId = (Long) args[0];
            Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotEligibleException("Customer not found: " + customerId));
            
            if (!customer.isEligibleForDiscount()) {
                throw new CustomerNotEligibleException("Customer " + customer.getName() + " is not eligible for discount");
            }
        }
    }
}
