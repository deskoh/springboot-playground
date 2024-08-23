package com.example.demo.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("rewards.client")
public class Settings {
    private final String host;
    private final int port;

    public Settings(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
