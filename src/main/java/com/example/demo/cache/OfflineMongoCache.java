package com.example.demo.cache;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.Nullable;

@Slf4j
public class OfflineMongoCache extends ConcurrentMapCache {

    private final MongoTemplate mongoTemplate;

    private final boolean offlineMode;

    public OfflineMongoCache(MongoTemplate mongoTemplate, String cacheName, boolean offlineMode) {
        super(cacheName);
        this.mongoTemplate = mongoTemplate;
        this.offlineMode = offlineMode;
    }

    @Override
    protected Object lookup(Object key) {
        var result = super.lookup(key);
        if (result == null && this.offlineMode) {
            // Try read from offline cache
            log.debug("cache miss for key {}, looking up offline cache", key);
            CacheEntry value = mongoTemplate.findById(key.toString(), CacheEntry.class, this.getName());
            if (value != null) {
                result = this.fromStoreValue(value.getValue());
                // Populate underlying cache with offline result
                super.put(key, result);
            }
        }
        return result;
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        log.debug("saving to offline cache for key {}", key);
        mongoTemplate.save(new CacheEntry(key.toString(), this.toStoreValue(value)), this.getName());
        super.put(key, value);
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection(this.getName());
        super.clear();
    }

    @Data
    @NoArgsConstructor
    private static class CacheEntry {
        private String id;
        private Object value;

        public CacheEntry(String id, Object value) {
            this.id = id;
            this.value = value;
        }
    }
}
