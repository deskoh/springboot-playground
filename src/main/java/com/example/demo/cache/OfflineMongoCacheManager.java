package com.example.demo.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class OfflineMongoCacheManager implements CacheManager {

    private static final String CACHE_PREFIX = "_cache_";
    private final ConcurrentHashMap<String, Cache> cacheMap = new ConcurrentHashMap<>();
    private final MongoTemplate mongoTemplate;
    private final boolean offlineMode;

    public OfflineMongoCacheManager(MongoTemplate mongoTemplate, boolean offlineMode) {
        this.mongoTemplate = mongoTemplate;
        this.offlineMode = offlineMode;
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        var nameWithPrefix = CACHE_PREFIX + name;
        return cacheMap.computeIfAbsent(name, n -> new OfflineMongoCache(mongoTemplate, nameWithPrefix, offlineMode));
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }
}
