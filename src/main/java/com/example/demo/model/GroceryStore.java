package com.example.demo.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
