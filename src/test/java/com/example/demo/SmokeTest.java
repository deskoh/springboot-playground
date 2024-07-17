package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(classes = DemoApplication.class)
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
