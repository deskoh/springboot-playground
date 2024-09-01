package com.example.demo.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@TestConfiguration
@Profile("!test-nodb")
public class JpaPopulator {
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
        var factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("SeedDataTest.json")});

        // Set custom mapper to support LocalDateTime deserialization
        var builder = new Jackson2ObjectMapperBuilder();
        var objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(new JavaTimeModule());
        factory.setMapper(objectMapper);

        return factory;
    }
}
