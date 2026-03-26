package com.example.demo.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoCacheManager implements CacheManager {

    private static final String CACHE_PREFIX = "_cache_";
    private final ConcurrentHashMap<String, Cache> cacheMap = new ConcurrentHashMap<>();
    private final MongoTemplate mongoTemplate;

    public MongoCacheManager(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Cache getCache(String name) {
        var nameWithPrefix = CACHE_PREFIX + name;
        return cacheMap.computeIfAbsent(name, n -> new MongoCache(mongoTemplate, nameWithPrefix));
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }
}
