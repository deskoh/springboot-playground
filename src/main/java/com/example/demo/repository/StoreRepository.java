package com.example.demo.repository;

import com.example.demo.model.GroceryStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@ConditionalOnBean(MongoTemplate.class)
public interface StoreRepository extends MongoRepository<GroceryStore, String> {
    List<GroceryStore> findByItemsItemNameIn(List<String> itemsWhitelist);

    @Query(value = "{ 'items.itemName': { $in: ?0 }}", fields = "{ storeName:  1 }")
    List<GroceryStore> findStoresWithItems(List<String> itemsWhitelist);

    @Aggregation(pipeline = {
            "{ $match: { 'items.itemName': { $in: ?0 }} }",
            "{ $project: { 'storeName': 1 } }"
    })
    List<String> findStoreNamesWithItems(List<String> itemsWhitelist);
}
