package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ext-sys")
@AllArgsConstructor
@Getter
@ToString
public class ExtSysConfig {
    @NonNull
    private final String url;
    private final int port;
    @NonNull
    private final String path;
    private String apiKey = "default";
}
