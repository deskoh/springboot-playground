package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "extSys.url=1.1.1.1",
        "extSys.port=8080",
        "extSys.apiKey=secret",
})
@ActiveProfiles("test")
public class ExtSysConfigTest {

    @Autowired
    private ExtSysConfig config;

    @Test
    void testConfigAbleToBindToProperties() {
        assertThat(config.getUrl()).isEqualTo("1.1.1.1");
        assertThat(config.getPort()).isEqualTo(8080);
        assertThat(config.getApiKey()).isEqualTo("secret");
    }

}
