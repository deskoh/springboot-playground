package com.example.demo;

import com.example.demo.config.Settings;
import com.example.demo.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
