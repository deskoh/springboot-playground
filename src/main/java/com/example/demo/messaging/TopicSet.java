package com.example.demo.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

// Shared shape for both backend providers and the frontend
@Validated
@Getter
@Setter
public class TopicSet {
    private Map<String, String> pub = new HashMap<>();
    private Map<String, String> sub = new HashMap<>(); // bound now, unused for now

    public Optional<String> pub(String key) {
        return Optional.ofNullable(pub.get(key));
    }
}
