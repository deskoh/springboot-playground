package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = "app.feature.new=false")
public class PostgresSessionServiceTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void givenFeatureToggleDisabled_sessionShouldBePostgres() {
        var service = context.getBean(SessionService.class);
        assertEquals("Data from PostgreSQL Database", service.getUserData());
    }
}
