package com.example.demo.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
    @Bean
    public WebClient localApiClient() {
        return WebClient.builder().build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnProperty(value = "app.seed-data", havingValue = "true")
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator(Jackson2ObjectMapperBuilder builder) {
        var factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{
                new ClassPathResource("seed/stores.json"),
                new ClassPathResource("seed/groceryitems.json")
        });

        // Set custom mapper to support LocalDateTime deserialization
        // var builder = new Jackson2ObjectMapperBuilder();
        var objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(new JavaTimeModule());
        factory.setMapper(objectMapper);

        return factory;
    }
}