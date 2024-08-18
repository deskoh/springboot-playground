package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionServiceTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    public void givenDefaultProperties_defaultSessionShouldBePostgres() {
        this.contextRunner
                .withBean(RedisSessionService.class)
                .withBean(PostgresSessionService.class)
                .run(context -> {
                    assertThat(context).hasBean("postgresSessionService");
                    var service = context.getBean(SessionService.class);
                    assertEquals("Data from PostgreSQL Database", service.getUserData());
                });
    }

    @Test
    public void givenFeatureToggleEnabled_sessionShouldBeRedis() {
        this.contextRunner
                .withPropertyValues("app.feature.new=true")
                .withBean(RedisSessionService.class)
                .withBean(PostgresSessionService.class)
                .run(context -> {
                    assertThat(context).hasBean("redisSessionService");
                    var service = context.getBean(SessionService.class);
                    assertEquals("Data from Redis", service.getUserData());
                });
    }

    @Test
    public void givenFeatureToggleDisabled_sessionShouldBePostgres() {
        this.contextRunner
                .withPropertyValues("app.feature.new=false")
                .withBean(RedisSessionService.class)
                .withBean(PostgresSessionService.class)
                .run(context -> {
                    assertThat(context).hasBean("postgresSessionService");
                    var service = context.getBean(SessionService.class);
                    assertEquals("Data from PostgreSQL Database", service.getUserData());
                });
    }
}
