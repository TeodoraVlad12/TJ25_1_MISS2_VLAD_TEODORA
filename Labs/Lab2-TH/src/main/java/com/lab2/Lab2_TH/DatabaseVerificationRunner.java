package com.lab2.Lab2_TH;

import com.lab2.Lab2_TH.properties.DatabaseProperties;
import com.lab2.Lab2_TH.service.DatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseVerificationRunner implements CommandLineRunner {

    private final DatabaseService databaseService;
    private final DatabaseProperties databaseProperties;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public DatabaseVerificationRunner(DatabaseService databaseService, DatabaseProperties databaseProperties) {
        this.databaseService = databaseService;
        this.databaseProperties = databaseProperties;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Command Line Arguments ===");
        if (args.length > 0) {
            System.out.println("Command-line arguments received:");
            for (int i = 0; i < args.length; i++) {
                System.out.println("  arg[" + i + "]: " + args[i]);
            }
        } else {
            System.out.println("No command-line arguments provided");
        }
        System.out.println("===============================");

        databaseService.initializeDatabase();

        System.out.println("=== Database Connection Verification ===");
        System.out.println("Active Profile: " + activeProfile);
        System.out.println("Host: " + databaseProperties.getHost());
        System.out.println("Port: " + databaseProperties.getPort());
        System.out.println("Database: " + databaseProperties.getDatabase());
        System.out.println("Username: " + databaseProperties.getUsername());
        System.out.println("URL: " + databaseProperties.getUrl());
        System.out.println("Driver: " + databaseProperties.getDriverClassName());

        try {
            System.out.println("App Info from DB: " + databaseService.getAppInfo());
            System.out.println("Connection Status: " + databaseService.getDatabaseInfo());
        } catch (Exception e) {
            System.out.println("Database connection issue: " + e.getMessage());
        }

        System.out.println("========================================");
    }
}
