package com.example.demo.service;

import com.example.demo.config.ExtSysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class ExternalRestClientService {
    private final RestClient restClient;
    private final ExtSysConfig config;

    // Example showing RestClient, WebClient, RestTemplate
    public ExternalRestClientService(RestClient.Builder restClientBuilder, ExtSysConfig config) {
        String baseUrl = "%s:%s".formatted(config.getUrl(), config.getPort());

        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.config = config;
    }

    // Calling external API using RestClient
    public String getData() {
        var path = config.getPath();
        log.info("Calling path: {}", path);
        return this.restClient.get()
                .uri(path)
                .retrieve()
                .body(String.class);
    }
}
