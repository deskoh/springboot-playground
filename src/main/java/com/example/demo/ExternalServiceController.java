package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ExternalServiceController {
    private final ExternalService service;


    public ExternalServiceController(ExternalService service) {
        this.service = service;
    }

    @GetMapping("/external")
    public Mono<ResponseEntity<String>> external() {
        return service.getData()
                .map(body -> ResponseEntity.ok("External: " + body));
    }

    @GetMapping("/external2")
    public ResponseEntity<String> external2() {
        return ResponseEntity.ok("External: " + service.getData2());
    }
}
