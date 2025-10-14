package com.Lab3.IC.Lab3.IC.service;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    
    public void sendNotification(String message) {
        System.out.println("[NOTIFICATION] " + message);
    }
}