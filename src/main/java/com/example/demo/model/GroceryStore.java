package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "stores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GroceryStore {
    @Id
    private String id;

    private String storeName;

    private String location;

    private List<GroceryItem> items;

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class GroceryItem {
        @Indexed
        private String itemName;

        private String category;

        private double price;

        private String brand;
    }
}
