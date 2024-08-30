package com.example.demo.repository;

import com.example.demo.model.GroceryStore;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StoreRepository extends MongoRepository<GroceryStore, String> {
    List<GroceryStore> findByItemsItemNameIn(List<String> itemsWhitelist);

    <T> List<T> findByItemsItemNameIn(List<String> itemsWhitelist, Class<T> type);

    @Query(value = "{ 'items.itemName': { $in: ?0 }}", fields = "{ storeName:  1 }")
    List<GroceryStore> findStoresWithItems(List<String> itemsWhitelist);

    @Aggregation(pipeline = {
            "{ $match: { 'items.itemName': { $in: ?0 }} }",
            "{ $project: { '_id' : '$storeName' } }"
            // "{ $project: { 'storeName': 1 } }"
    })
    List<String> findStoreNamesWithItems(List<String> itemsWhitelist);

    @Aggregation("{ '$project': { '_id' : '$storeName' } }")
    List<String> findStoreNamesWithItems2(List<String> itemsWhitelist);
}
