package com.example.demo.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.feature.new", havingValue = "false", matchIfMissing = true)
public class PostgresSessionService implements SessionService {
    @Override
    public String getUserData() {
        return "Data from PostgreSQL Database";
    }
}