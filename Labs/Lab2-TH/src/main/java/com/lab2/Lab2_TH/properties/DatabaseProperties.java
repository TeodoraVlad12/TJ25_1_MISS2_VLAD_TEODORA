package com.lab2.Lab2_TH.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DatabaseProperties {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String url;
    private String driverClassName;
}
