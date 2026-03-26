package com.example.demo.controller;

import com.example.demo.service.ExternalRestClientService;
import com.example.demo.service.ExternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CacheConfig(cacheManager = "mongoCacheManager")
public class ExternalServiceController {
    private final ExternalService service;
    private final ExternalRestClientService restClientService;

    public ExternalServiceController(ExternalService service, ExternalRestClientService restClientService) {
        this.service = service;
        this.restClientService = restClientService;
    }

    @GetMapping("/external")
    public Mono<String> external() {
        return service.getData().map(body -> "External: " + body);
    }

    @Cacheable(cacheNames = "external")
    @GetMapping("/external/cached")
    public Mono<String> externalCached() {
        return service.getData().map(body -> "External: " + body);
    }

    @CachePut("external")
    @GetMapping("/external/cachePut")
    public Mono<String> externalCachedPut() {
        return service.getData().map(body -> "External: " + body);
    }

    @CacheEvict(value = "external", allEntries = true)
    @GetMapping("/external/clearCache")
    public String externalCachedEvict() {
        return "Cache cleared";
    }

    @GetMapping("/external2")
    public ResponseEntity<String> external2() {
        return ResponseEntity.ok("External: " + service.getData2());
    }

    @GetMapping("/external3")
    public ResponseEntity<String> external3() {
        return ResponseEntity.ok("External: " + restClientService.getData());
    }
}
