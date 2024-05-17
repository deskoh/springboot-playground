package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class PropertiesTest {

    @Autowired
    private Environment env;

    @Value("${server.port}")
    private int port;

    @Test
    void testValueAnnotation() {
        assertThat(port).isEqualTo(8080);
    }

    @Test
    void testEnvironmentClass() {
        assertThat(env.getProperty("server.port")).isEqualTo("8080");
        assertThat(env.getProperty("server.port", Integer.class)).isEqualTo(8080);
    }

}
