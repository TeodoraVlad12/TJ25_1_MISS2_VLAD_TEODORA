package com.lab2.Lab2_TH.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;
    private final String activeProfile;

    public DatabaseService(JdbcTemplate jdbcTemplate, @Value("${spring.profiles.active:default}") String activeProfile) {
        this.jdbcTemplate = jdbcTemplate;
        this.activeProfile = activeProfile;
    }

    public void initializeDatabase() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS app_info (id INT PRIMARY KEY, profile VARCHAR(50), database_name VARCHAR(100))");
        
        jdbcTemplate.execute("MERGE INTO app_info (id, profile, database_name) VALUES (1, '" + activeProfile + "', '" + activeProfile + "_database')");
    }

    public List<Map<String, Object>> getAppInfo() {
        return jdbcTemplate.queryForList("SELECT * FROM app_info");
    }

    public String getDatabaseInfo() {
        try {
            Map<String, Object> info = jdbcTemplate.queryForMap("SELECT profile, database_name FROM app_info WHERE id = 1");
            return "Profile: " + info.get("profile") + ", Database: " + info.get("database_name");
        } catch (Exception e) {
            return "Profile: " + activeProfile + ", Database: " + activeProfile + "_database (from memory)";
        }
    }

    public String getActiveProfile() {
        return activeProfile;
    }
}
