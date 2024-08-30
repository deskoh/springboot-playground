package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class)
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
