package com.example.demo.messaging;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

interface MessageTransport {
    TransportType type();

    void send(String topic, Object payload);
}

@Service
public class MessagePublisher {
    private final TopicResolver resolver;
    private final Map<TransportType, MessageTransport> transports;

    // TODO: injection works for list??
    MessagePublisher(TopicResolver resolver, List<MessageTransport> all) {
        this.resolver = resolver;
        this.transports = all.stream().collect(toMap(MessageTransport::type, identity()));
    }

    public void publish(MessagingEvent event, Object payload) {
        resolver.resolve(event).forEach(d -> transports.get(d.transport()).send(d.topic(), payload));
    }
}

@Component
class MqttTransport implements MessageTransport {
    public TransportType type() {
        return TransportType.MQTT;
    }

    public void send(String topic, Object payload) {
        /* mqttClient.publish(...) */
    }
}

@Component
@ConditionalOnProperty(name = "messaging.backend.provider", havingValue = "kafka")
class KafkaTransport implements MessageTransport {
    public TransportType type() {
        return TransportType.KAFKA;
    }

    public void send(String topic, Object payload) {
        /* kafkaTemplate.send(...) */
    }
}
