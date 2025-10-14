package com.Lab3.IC.Lab3.IC.service;

import org.springframework.stereotype.Component;

@Component
public class LoggingService {
    
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}