package com.example.demo.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.Nullable;

public class MongoCache implements Cache {

    private final MongoTemplate mongoTemplate;
    private final String cacheName;

    public MongoCache(MongoTemplate mongoTemplate, String cacheName) {
        this.mongoTemplate = mongoTemplate;
        this.cacheName = cacheName;
    }

    @Override
    public final String getName() {
        return this.cacheName;
    }

    @Override
    public final MongoTemplate getNativeCache() {
        return mongoTemplate;
    }

    @Nullable
    private Object lookup(Object key) {
        CacheEntry result = mongoTemplate.findById(key.toString(), CacheEntry.class, this.cacheName);
        return result != null ? result.getValue() : null;
    }

    @Override
    @Nullable
    public ValueWrapper get(Object key) {
        Object value = this.lookup(key);
        return (value != null) ? new SimpleValueWrapper(value) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T get(Object key, @Nullable Class<T> type) {
        Object value = this.lookup(key);
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        } else {
            return (T) value;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T result = (T) this.lookup(key);
        if (result == null) {
            try {
                result = valueLoader.call();
                this.put(key, result);
            } catch (Exception e) {
                throw new Cache.ValueRetrievalException(key, valueLoader, e);
            }
        }
        return result;
    }

    @Override
    public CompletableFuture<?> retrieve(Object key) {
        Object value = this.lookup(key);
        return value != null ? CompletableFuture.completedFuture(value) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> retrieve(Object key, Supplier<CompletableFuture<T>> valueLoader) {
        return CompletableFuture.supplyAsync(() -> {
            var result = (T) this.lookup(key);
            if (result == null) {
                result = (valueLoader.get().join());
                this.put(key, result);
            }
            return result;
        });
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        mongoTemplate.save(new CacheEntry(key.toString(), value), this.cacheName);
    }

    @Override
    public void evict(Object key) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(key.toString())), CacheEntry.class);
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection(this.cacheName);
    }

    @Data
    @NoArgsConstructor
    private static class CacheEntry {
        private String id;
        private Object value;

        public CacheEntry(String id, @Nullable Object value) {
            this.id = id;
            this.value = value;
        }
    }
}
