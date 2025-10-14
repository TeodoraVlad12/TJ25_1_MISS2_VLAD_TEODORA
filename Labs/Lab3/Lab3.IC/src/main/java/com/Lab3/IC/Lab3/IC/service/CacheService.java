package com.Lab3.IC.Lab3.IC.service;

import org.springframework.stereotype.Component;

@Component
public class CacheService {
    
    public String getCachedData() {
        return "Cached data";
    }
}