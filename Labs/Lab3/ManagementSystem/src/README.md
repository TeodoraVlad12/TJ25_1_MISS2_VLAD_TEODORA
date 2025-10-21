# Spring Boot Order Management System

- Different discount strategies based on Spring profiles
- In-memory repository with customer data
- Orders handling with discount application
- Customer eligibility validation using aspects
- Event publishing and listening for large discounts (over 80)
- Logging of discount operations

## Discount Strategies

Change the active profile in `application.properties`:

### 1. Loyal Customer Discount (`loyal` profile)
- 10% percentage discount for loyal customers
- Applied when customer.isLoyal() returns true

### 2. Large Order Discount (`large-order` profile)
- Fixed $100 discount for orders >= $1000
- Applied regardless of customer loyalty status

### 3. No Discount (`no-discount` profile)
- Default strategy with no discounts applied
