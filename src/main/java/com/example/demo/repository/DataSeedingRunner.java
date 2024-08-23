package com.example.demo.repository;

import com.example.demo.model.GroceryItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("seed-db")
@Component
public class DataSeedingRunner implements CommandLineRunner {

    private final boolean seedData;
    private final ItemRepository groceryItemRepo;
    private final StoreRepository storeRepo;

    public DataSeedingRunner(
            ItemRepository groceryItemRepo,
            StoreRepository storeRepo,
            @Value("${app.seed-data}") boolean seedData) {
        this.groceryItemRepo = groceryItemRepo;
        this.storeRepo = storeRepo;
        this.seedData = seedData;
    }

    @Override
    public void run(String... args) {
        if (!this.seedData) return;
        if (groceryItemRepo != null) {
            System.out.println("-----CREATE GROCERY ITEMS-----\n");
            createGroceryItems();
            System.out.println("\n-----SHOW ALL GROCERY ITEMS-----\n");
            showAllGroceryItems();
            System.out.println("\n-----GET ITEM BY NAME-----\n");
            getGroceryItemByName("Whole Wheat Biscuit");
            System.out.println("\n-----GET ITEMS BY CATEGORY-----\n");
            getItemsByCategory("millets");
            System.out.println("\n-----UPDATE CATEGORY NAME OF SNACKS CATEGORY-----\n");
            updateCategoryName("snacks");
            System.out.println("\n-----DELETE A GROCERY ITEM-----\n");
            deleteGroceryItem("Kodo Millet");
            System.out.println("\n-----FINAL COUNT OF GROCERY ITEMS-----\n");
            findCountOfGroceryItems();
            System.out.println("\n-----THANK YOU-----");
        }
        if (storeRepo != null) {
            System.out.println("-----CREATE STORE-----\n");
            createGroceryStores();
        }

    }

    // Print details in readable form
    public String getItemDetails(GroceryItem item) {
        return "Item Name: %s, \nQuantity: %s, \nItem Category: %s".formatted(item.getName(), item.getQuantity(), item.getCategory());
    }

    private void createGroceryItems() {
        System.out.println("Data creation started...");
        groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
        groceryItemRepo.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
        groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
        groceryItemRepo.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
        groceryItemRepo.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
        System.out.println("Data creation complete...");
    }

    // READ
    // 1. Show all the data
    public void showAllGroceryItems() {
        groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
    }

    // 2. Get item by name
    public void getGroceryItemByName(String name) {
        System.out.println("Getting item by name: " + name);
        GroceryItem item = groceryItemRepo.findItemByName(name);
        System.out.println(getItemDetails(item));
    }

    // 3. Get name and quantity of a all items of a particular category
    public void getItemsByCategory(String category) {
        System.out.println("Getting items for the category " + category);
        var list = groceryItemRepo.findAll(category);
        list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getQuantity()));
    }

    // 4. Get count of documents in the collection
    public void findCountOfGroceryItems() {
        long count = groceryItemRepo.count();
        System.out.println("Number of documents in the collection: " + count);
    }

    public void updateCategoryName(String category) {
        // Change to this new value
        String newCategory = "munchies";

        // Find all the items with the category snacks
        var list = groceryItemRepo.findAll(category);

        list.forEach(item -> {
            // Update the category in each document
            item.setCategory(newCategory);
        });

        // Save all the items in database
        var itemsUpdated = groceryItemRepo.saveAll(list);

        System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
    }

    // DELETE
    public void deleteGroceryItem(String id) {
        groceryItemRepo.deleteById(id);
        System.out.println("Item with id " + id + " deleted...");
    }
}
