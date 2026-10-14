package com.example.demo.messaging;

import com.example.demo.config.TopicsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class MessagePublisherTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withInitializer(
                    // Allow @Value lowercase binding to enum
                    ctx -> ctx.getBeanFactory().setConversionService(ApplicationConversionService.getSharedInstance()))
            .withUserConfiguration(MessagingScanConfig.class, TopicsConfig.class);

    @Test
    void messagePublisherBeanCanBeCreated() {
        runner.withPropertyValues(
                        "messaging.backend.provider=kafka",
                        "messaging.backend.topics.kafka.pub.order-created=kafka-orders",
                        "messaging.backend.topics.kafka.pub.payment=kafka-payment",
                        "messaging.frontend.topics.pub.notification=mqtt/notifications",
                        "messaging.frontend.topics.pub.order-created=mqtt/orders")
                .run(context -> {
                    var publisher = context.getBean(MessagePublisher.class);
                    System.out.println(publisher);
                });
    }

    @Configuration
    @ComponentScan("com.example.demo.messaging")
    @EnableConfigurationProperties // registers the binder for @ConfigurationProperties on @Bean methods
    static class MessagingScanConfig {}
}
