package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoApplication.class)
@ActiveProfiles("test")
class TestingWebApplicationTests {

    @Autowired
    private HelloController controller;

    @Autowired
    private Settings settings;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(settings).isNotNull();
        assertThat(settings.getHost()).isEqualTo("127.0.0.1");
        assertThat(settings.getPort()).isEqualTo(80);
    }
}
