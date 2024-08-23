package com.example.demo.controller;

import com.example.demo.service.ExternalRestClientService;
import com.example.demo.service.ExternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ExternalServiceController {
    private final ExternalService service;
    private final ExternalRestClientService restClientService;


    public ExternalServiceController(ExternalService service, ExternalRestClientService restClientService) {
        this.service = service;
        this.restClientService = restClientService;
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

    @GetMapping("/external3")
    public ResponseEntity<String> external3() {
        return ResponseEntity.ok("External: " + restClientService.getData());
    }
}
