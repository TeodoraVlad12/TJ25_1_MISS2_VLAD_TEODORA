package com.lab2.Lab2_TH.actuator;

import com.lab2.Lab2_TH.properties.DatabaseProperties;
import com.lab2.Lab2_TH.service.DatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DatabaseActuator {

    private final DatabaseService databaseService;
    private final DatabaseProperties databaseProperties;

    public DatabaseActuator(DatabaseService databaseService, DatabaseProperties databaseProperties) {
        this.databaseService = databaseService;
        this.databaseProperties = databaseProperties;
    }

    @GetMapping("/actuator/database")
    public Map<String, Object> databaseInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("host", databaseProperties.getHost());
        info.put("database", databaseProperties.getDatabase());
        info.put("activeProfile", databaseService.getActiveProfile());
        info.put("connectionStatus", databaseService.getDatabaseInfo());
        return info;
    }
}