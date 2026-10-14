package com.example.demo.messaging;

import lombok.Getter;

enum Audience {
    FRONTEND,
    BACKEND,
    BOTH
}

@Getter
public enum MessagingEvent {
    ORDER_CREATED("order-created", Audience.BOTH),
    PAYMENT("payment", Audience.BACKEND),
    NOTIFICATION("notification", Audience.FRONTEND);

    private final String key;
    private final Audience audience;

    MessagingEvent(String key, Audience audience) {
        this.key = key;
        this.audience = audience;
    }

    boolean isBackend() {
        return audience != Audience.FRONTEND;
    }

    boolean isFrontend() {
        return audience != Audience.BACKEND;
    }
}
