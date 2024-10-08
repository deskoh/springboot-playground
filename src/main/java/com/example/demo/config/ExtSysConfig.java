package com.example.demo.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ext-sys")
@RequiredArgsConstructor
@Getter
@ToString
public class ExtSysConfig {
    private final String url;
    private final int port;
    private final String path;
    private final String apiKey;
}
