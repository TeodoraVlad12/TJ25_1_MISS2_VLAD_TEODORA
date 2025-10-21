package ManagementSystem.example.ManagementSystem.controller;

import ManagementSystem.example.ManagementSystem.dto.ErrorResponse;
import ManagementSystem.example.ManagementSystem.dto.OrderRequest;
import ManagementSystem.example.ManagementSystem.exception.CustomerNotEligibleException;
import ManagementSystem.example.ManagementSystem.model.Order;
import ManagementSystem.example.ManagementSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/process")
    public ResponseEntity<?> processOrder(@RequestBody OrderRequest request) {
        try {
            Order order = orderService.processOrder(request.getCustomerId(), request.getAmount());
            return ResponseEntity.ok(order);
        } catch (CustomerNotEligibleException e) {
            ErrorResponse error = new ErrorResponse("CUSTOMER_NOT_ELIGIBLE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse("CUSTOMER_NOT_FOUND", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
