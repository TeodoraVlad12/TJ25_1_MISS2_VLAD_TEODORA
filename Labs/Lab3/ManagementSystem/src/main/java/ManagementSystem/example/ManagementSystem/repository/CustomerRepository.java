package ManagementSystem.example.ManagementSystem.repository;

import ManagementSystem.example.ManagementSystem.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerRepository {
    
    private final Map<Long, Customer> customers = new HashMap<>();
    
    public CustomerRepository() {
        customers.put(1L, new Customer(1L, "Ana Pop", true, true));
        customers.put(2L, new Customer(2L, "George Ionescu", false, true));
        customers.put(3L, new Customer(3L, "Mihaela Vasile", true, false));
        customers.put(4L, new Customer(4L, "Robert Tatar", false, true));
    }
    
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customers.get(id));
    }
    
    public Customer save(Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }
}
