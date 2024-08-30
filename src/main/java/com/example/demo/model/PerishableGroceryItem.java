package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("groceryitems")
@NoArgsConstructor
@Getter
@Setter
public class PerishableGroceryItem extends GroceryItem {

    private LocalDate expirationDate;

    private boolean refrigerated;

    public PerishableGroceryItem(
            String id, String name, int quantity, String category, LocalDate expirationDate, boolean refrigerated
    ) {
        super(id, name, quantity, category);
        this.expirationDate = expirationDate;
        this.refrigerated = refrigerated;
    }

}