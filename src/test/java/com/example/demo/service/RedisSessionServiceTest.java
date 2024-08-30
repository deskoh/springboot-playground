package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = "app.feature.new=true")
public class RedisSessionServiceTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void givenFeatureToggleEnabled_sessionShouldBeRedis() {
        var service = context.getBean(SessionService.class);
        assertEquals("Data from Redis", service.getUserData());
    }
}
