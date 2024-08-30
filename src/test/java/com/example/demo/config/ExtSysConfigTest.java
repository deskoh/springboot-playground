package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

// configure initializers to load properties from application.properties
@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(properties = {
        "extSys.url=1.1.1.1",
        "extSys.port=8080",
        "extSys.apiKey=secret",
})
@EnableConfigurationProperties(ExtSysConfig.class)
public class ExtSysConfigTest {
    @Test
    void testConfigAbleToBindToProperties(@Autowired ExtSysConfig config) {
        assertThat(config.getUrl()).isEqualTo("1.1.1.1");
        assertThat(config.getPort()).isEqualTo(8080);
        assertThat(config.getApiKey()).isEqualTo("secret");
    }
}
