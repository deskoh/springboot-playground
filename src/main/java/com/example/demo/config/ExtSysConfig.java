package com.example.demo.config;

import lombok.NonNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ext-sys")
@Value
public class ExtSysConfig {
    @NonNull
    private final String url;

    private final int port;

    @NonNull
    private final String path;

    private String apiKey;
}
