package com.example.demo.messaging;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class TopicResolver {
    private final TopicSet backend, frontend;
    private final TransportType backendTransport;

    TopicResolver(
            @Qualifier("backendTopics") TopicSet backend,
            @Qualifier("frontendTopics") TopicSet frontend,
            @Value("${messaging.backend.provider:kafka}") TransportType backendTransport) {
        this.backend = backend;
        this.frontend = frontend;
        this.backendTransport = backendTransport;
    }

    private static String require(Optional<String> topic, MessagingEvent e, String side) {
        return topic.orElseThrow(() ->
                new IllegalStateException("No " + side + " pub topic configured for event key '" + e.getKey() + "'"));
    }

    Set<Destination> resolve(MessagingEvent e) {
        Set<Destination> out = new LinkedHashSet<>(); // record equality = dedup
        if (e.isBackend()) {
            out.add(new Destination(backendTransport, require(backend.pub(e.getKey()), e, "backend")));
        }
        if (e.isFrontend()) {
            out.add(new Destination(TransportType.MQTT, require(frontend.pub(e.getKey()), e, "frontend")));
        }
        return out;
    }
}

/**
 * Config check at startup.
 */
@Component
class TopicConfigValidator implements InitializingBean {
    private final TopicResolver resolver;

    TopicConfigValidator(TopicResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void afterPropertiesSet() {
        for (var e : MessagingEvent.values()) resolver.resolve(e); // throws if misconfigured
    }
}
