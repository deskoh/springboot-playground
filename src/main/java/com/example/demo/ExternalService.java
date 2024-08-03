package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ExternalService {
    private final WebClient webclient;
    private final ExtSysConfig config;
    private final RestTemplate restTemplate;

    public ExternalService(WebClient webClient, RestTemplate restTemplate, ExtSysConfig config) {
        this.webclient = webClient;
        this.restTemplate = restTemplate;
        this.config = config;
    }

    // Calling external API using WebClient
    public Mono<String> getData() {
        String url = "%s:%s%s".formatted(config.getUrl(), config.getPort(), config.getPath());
        log.info("Calling URL: {}", url);
        return this.webclient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Calling external API using RestTemplate
    public String getData2() {
        String url = "%s:%s%s".formatted(config.getUrl(), config.getPort(), config.getPath());
        log.info("Calling URL: {}", url);
        return this.restTemplate.getForObject(url, String.class);
    }
}
