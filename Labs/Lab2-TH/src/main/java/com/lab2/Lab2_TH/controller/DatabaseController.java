package com.lab2.Lab2_TH.controller;

import com.lab2.Lab2_TH.properties.DatabaseProperties;
import com.lab2.Lab2_TH.service.DatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DatabaseController {

    private final DatabaseService databaseService;
    private final DatabaseProperties databaseProperties;

    public DatabaseController(DatabaseService databaseService, DatabaseProperties databaseProperties) {
        this.databaseService = databaseService;
        this.databaseProperties = databaseProperties;
    }

    @GetMapping("/db-info")
    public Map<String, Object> getDatabaseInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("connectionInfo", databaseService.getDatabaseInfo());
        info.put("host", databaseProperties.getHost());
        info.put("port", databaseProperties.getPort());
        info.put("database", databaseProperties.getDatabase());
        info.put("username", databaseProperties.getUsername());
        info.put("activeProfile", databaseService.getActiveProfile());
        return info;
    }

    @GetMapping("/app-data")
    public List<Map<String, Object>> getAppData() {
        return databaseService.getAppInfo();
    }
}
