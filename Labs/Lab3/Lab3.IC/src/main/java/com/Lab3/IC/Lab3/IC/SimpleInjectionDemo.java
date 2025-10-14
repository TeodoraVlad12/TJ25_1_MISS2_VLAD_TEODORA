package com.Lab3.IC.Lab3.IC;

import com.Lab3.IC.Lab3.IC.service.DatabaseService;
import com.Lab3.IC.Lab3.IC.service.LoggingService;
import com.Lab3.IC.Lab3.IC.service.CacheService;
import com.Lab3.IC.Lab3.IC.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class SimpleInjectionDemo {

    // 1. CONSTRUCTOR INJECTION
    private final DatabaseService constructorService;

    // 2. FIELD INJECTION
    @Autowired
    private LoggingService fieldService;

    // 3. SETTER INJECTION
    private CacheService setterService;

    // 4. METHOD INJECTION
    private NotificationService methodService;

    // Constructor injection
    public SimpleInjectionDemo(DatabaseService databaseService) {
        this.constructorService = databaseService;
        System.out.println("1. CONSTRUCTOR INJECTION executed");
        System.out.println(fieldService);
    }

    // Setter injection
    @Autowired(required = false)
    public void setSetterService(CacheService cacheService) {
        this.setterService = cacheService;
        System.out.println("2. FIELD INJECTION executed " + fieldService);
        System.out.println("3. SETTER INJECTION executed");
    }

    // Method injection - happens after setter injection
    @Autowired(required = false)
    public void configureMethodService(NotificationService notificationService) {
        this.methodService = notificationService;
        System.out.println("4. METHOD INJECTION executed");
    }

    @PostConstruct
    public void init() {
        System.out.println("2. FIELD INJECTION executed (verified in @PostConstruct)");
    }
}