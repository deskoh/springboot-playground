package com.example.demo.repository;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Profile("firstRun")
@Component
public class MongoIndexCreator implements ApplicationListener<ContextRefreshedEvent> {

    private MongoTemplate mongoTemplate;

    public MongoIndexCreator(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // mongoTemplate.indexOps(GroceryItem.class).ensureIndex(new Index().on("category", Sort.Direction.ASC));

        var mappingContext = mongoTemplate.getConverter().getMappingContext();

        var resolver = new MongoPersistentEntityIndexResolver(mappingContext);

        // consider only entities that are annotated with @Document
        mappingContext.getPersistentEntities()
                .stream()
                .filter(it -> it.isAnnotationPresent(Document.class))
                .forEach(it -> {
                    var indexOps = mongoTemplate.indexOps(it.getType());
                    resolver.resolveIndexFor(it.getType()).forEach(indexOps::ensureIndex);
                });
    }
}