package com.example.demo;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "other")
public class MyActuator {
    @ReadOperation
    public Map<String, Object> getOtherValues() {
        return Map.of("greeting", "Hello Actuator!");
    }
}