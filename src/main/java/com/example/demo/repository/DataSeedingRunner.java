package com.example.demo.repository;

import com.example.demo.model.GroceryItem;
import com.example.demo.model.GroceryStore;
import com.example.demo.model.PerishableGroceryItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
        groceryItemRepo.save(new PerishableGroceryItem("Milk", "Low Fat Milk", 2, "Dairy", LocalDate.now().plusDays(7), true));
        System.out.println("Data creation complete...");
    }

    private void createGroceryStores() {
        storeRepo.save(GroceryStore.builder().id("1").storeName("Green Grocer").location("Downtown")
                .items(Arrays.asList(
                        new GroceryStore.GroceryItem("Apple", "Fruits", 0.99, "FarmFresh"),
                        new GroceryStore.GroceryItem("Milk", "Dairy", 2.49, "DairyPure")
                )).build());
        storeRepo.save(GroceryStore.builder().id("2").storeName("Healthy Harvest").location("Uptown")
                .items(List.of(
                        new GroceryStore.GroceryItem("Banana", "Fruits", 1.79, "Chiquita")
                )).build());
        storeRepo.save(GroceryStore.builder().id("3").storeName("Market Fresh").location("Midtown")
                .items(Arrays.asList(
                        new GroceryStore.GroceryItem("Orange", "Fruits", 1.29, "SunKist"),
                        new GroceryStore.GroceryItem("Spinach", "Vegetables", 1.79, "Earthbound Farm")
                )).build());
        storeRepo.save(GroceryStore.builder().id("4").storeName("Farmers Market").location("Suburbs")
                .items(Arrays.asList(
                        new GroceryStore.GroceryItem("Yogurt", "Dairy", 2.49, "Chobani"),
                        new GroceryStore.GroceryItem("Potato", "Vegetables", 1.79, "Russet")
                )).build());
        storeRepo.save(GroceryStore.builder().id("5").storeName("City Mart").location("City Center")
                .items(Arrays.asList(
                        new GroceryStore.GroceryItem("Grapes", "Fruits", 2.49, "Del Monte"),
                        new GroceryStore.GroceryItem("Butter", "Dairy", 3.49, "Land O'Lakes"),
                        new GroceryStore.GroceryItem("Cucumber", "Vegetables", 0.89, "NatureSweet")
                )).build());

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
