package ManagementSystem.example.ManagementSystem.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountEvent {
    private String customerName;
    private double discountAmount;
    private String discountType;
}
