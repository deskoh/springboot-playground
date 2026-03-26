package com.example.demo.config;

import com.example.demo.cache.MongoCacheManager;
import com.example.demo.cache.OfflineMongoCacheManager;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${offlineMongoCache.offlineMode}")
    private boolean offlineMode;

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
        factory.setResources(new Resource[] {
            new ClassPathResource("seed/stores.json"), new ClassPathResource("seed/groceryitems.json")
        });

        // Set custom mapper to support LocalDateTime deserialization
        // var builder = new Jackson2ObjectMapperBuilder();
        var objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(new JavaTimeModule());
        factory.setMapper(objectMapper);

        return factory;
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        log.info("Using ConcurrentMapCacheManager");
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public MongoCacheManager mongoCacheManager() {
        log.info("Using MongoCacheManager");
        return new MongoCacheManager(mongoTemplate);
    }

    @Bean
    public OfflineMongoCacheManager offlineMongoCacheManager() {
        log.info("Using OfflineMongoCacheManager (offlineMode: {})", offlineMode);
        return new OfflineMongoCacheManager(mongoTemplate, offlineMode);
    }
}
