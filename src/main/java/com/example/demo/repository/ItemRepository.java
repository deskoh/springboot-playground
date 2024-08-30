package com.example.demo.repository;

import com.example.demo.model.GroceryItem;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<GroceryItem, String> {

    GroceryItem findItemByName(String name);

    boolean existsItemByName(String name);

    int countItemByName(String name);

    int countItemByCategory(String category);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1, 'quantity' : 1, 'category': 1}")
    List<GroceryItem> findAll(String category);

    long count();

    @Aggregation("{ $group : { _id : null, total : { $sum : $quantity } } }")
    Long countTotalQuantity();

    @Aggregation({
            "{ $match: { 'category': ?0 } }",
            "{ $group : { _id : null, total : { $sum : $quantity } } }"
    })
    Long countTotalQuantityByCategory(String category);
}