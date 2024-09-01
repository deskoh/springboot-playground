package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ext-sys")
public record ExtSysConfigRecord(String url, int port, String path, String apiKey) {
}