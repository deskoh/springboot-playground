package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("groceryitems")
@AllArgsConstructor
@Getter
@Setter
public class GroceryItem {

    @Id
    private String id;

    private String name;
    private int quantity;

    @Indexed
    private String category;
}