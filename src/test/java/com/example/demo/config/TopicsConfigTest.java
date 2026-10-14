package com.example.demo.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.messaging.TopicSet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

class TopicsConfigTest {

    private final ApplicationContextRunner runner =
            new ApplicationContextRunner().withUserConfiguration(BindingConfig.class, TopicsConfig.class);

    @Test
    void defaultsToKafkaWhenProviderMissing() {
        runner.withPropertyValues(
                        "messaging.backend.topics.kafka.pub.orders=kafka-orders",
                        "messaging.backend.topics.mqtt.pub.orders=mqtt/orders")
                .run(context -> {
                    TopicSet backend = context.getBean("backendTopics", TopicSet.class);
                    assertThat(backend.pub("orders")).contains("kafka-orders");
                });
    }

    @Test
    void usesKafkaWhenProviderIsKafka() {
        runner.withPropertyValues(
                        "messaging.backend.provider=kafka",
                        "messaging.backend.topics.kafka.pub.orders=kafka-orders",
                        "messaging.backend.topics.kafka.sub.events=kafka-events",
                        "messaging.backend.topics.mqtt.pub.orders=mqtt/orders")
                .run(context -> {
                    assertThat(context).hasBean("backendTopics");
                    TopicSet backend = context.getBean("backendTopics", TopicSet.class);
                    assertThat(backend.getPub()).containsEntry("orders", "kafka-orders");
                    assertThat(backend.getSub()).containsEntry("events", "kafka-events");
                });
    }

    @Test
    void usesMqttWhenProviderIsMqtt() {
        runner.withPropertyValues(
                        "messaging.backend.provider=mqtt",
                        "messaging.backend.topics.mqtt.pub.orders=mqtt/orders",
                        "messaging.backend.topics.mqtt.sub.events=mqtt/events",
                        "messaging.backend.topics.kafka.pub.orders=kafka-orders")
                .run(context -> {
                    TopicSet backend = context.getBean("backendTopics", TopicSet.class);
                    assertThat(backend.getPub()).containsEntry("orders", "mqtt/orders");
                    assertThat(backend.getSub()).containsEntry("events", "mqtt/events");
                    assertThat(backend.pub("orders")).contains("mqtt/orders");
                });
    }

    @Test
    void fallsBackToNoBackendBeanWhenProviderUnknown() {
        runner.withPropertyValues("messaging.backend.provider=rabbit").run(context -> {
            assertThat(context).doesNotHaveBean("backendTopics");
            assertThat(context).hasBean("frontendTopics");
        });
    }

    @Test
    void providerValueMatchingIsCaseInsensitive() {
        // ConditionalOnProperty compares havingValue case-insensitively
        runner.withPropertyValues(
                        "messaging.backend.provider=MQTT", "messaging.backend.topics.mqtt.pub.orders=mqtt/orders")
                .run(context -> {
                    TopicSet backend = context.getBean("backendTopics", TopicSet.class);
                    assertThat(backend.pub("orders")).contains("mqtt/orders");
                });
    }

    @Test
    void onlyOneBackendBeanIsRegistered() {
        runner.withPropertyValues("messaging.backend.provider=mqtt").run(context -> {
            assertThat(context.getBeansOfType(TopicSet.class)).containsOnlyKeys("backendTopics", "frontendTopics");
        });
    }

    @Test
    void bindsFrontendTopics() {
        runner.withPropertyValues(
                        "messaging.frontend.topics.pub.alerts=ui/alerts",
                        "messaging.frontend.topics.sub.commands=ui/commands")
                .run(context -> {
                    TopicSet frontend = context.getBean("frontendTopics", TopicSet.class);
                    assertThat(frontend.getPub()).containsEntry("alerts", "ui/alerts");
                    assertThat(frontend.getSub()).containsEntry("commands", "ui/commands");
                });
    }

    @Test
    void frontendTopicsAreIndependentOfBackendProvider() {
        runner.withPropertyValues(
                        "messaging.backend.provider=mqtt",
                        "messaging.backend.topics.mqtt.pub.orders=mqtt/orders",
                        "messaging.frontend.topics.pub.alerts=ui/alerts")
                .run(context -> {
                    TopicSet backend = context.getBean("backendTopics", TopicSet.class);
                    TopicSet frontend = context.getBean("frontendTopics", TopicSet.class);
                    assertThat(backend).isNotSameAs(frontend);
                    assertThat(backend.getPub()).doesNotContainKey("alerts");
                    assertThat(frontend.getPub()).doesNotContainKey("orders");
                });
    }

    @Test
    void emptyTopicSetsWhenNoPropertiesProvided() {
        runner.run(context -> {
            TopicSet backend = context.getBean("backendTopics", TopicSet.class);
            TopicSet frontend = context.getBean("frontendTopics", TopicSet.class);
            assertThat(backend.getPub()).isEmpty();
            assertThat(backend.getSub()).isEmpty();
            assertThat(frontend.getPub()).isEmpty();
            assertThat(frontend.getSub()).isEmpty();
            assertThat(backend.pub("missing")).isEmpty();
        });
    }

    // registers the ConfigurationPropertiesBindingPostProcessor needed for @ConfigurationProperties on @Bean methods
    @Configuration
    @EnableConfigurationProperties
    static class BindingConfig {}
}
