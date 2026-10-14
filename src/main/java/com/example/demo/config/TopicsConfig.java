package com.example.demo.config;

import com.example.demo.messaging.TopicSet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TopicsConfig {

    @Bean(name = "backendTopics")
    @ConditionalOnProperty(name = "messaging.backend.provider", havingValue = "mqtt")
    @ConfigurationProperties("messaging.backend.topics.mqtt")
    TopicSet backendTopicsMqtt() {
        return new TopicSet();
    }

    @Bean(name = "backendTopics")
    @ConditionalOnProperty(name = "messaging.backend.provider", havingValue = "kafka", matchIfMissing = true)
    @ConfigurationProperties("messaging.backend.topics.kafka")
    TopicSet backendTopicsKafka() {
        return new TopicSet();
    }

    @Bean(name = "frontendTopics")
    @ConfigurationProperties("messaging.frontend.topics")
    TopicSet frontendTopics() {
        return new TopicSet();
    }
}
