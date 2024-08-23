package com.example.demo.repository;

import com.example.demo.model.GroceryStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test-db")
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @BeforeEach
    public void setUp() {
        var runner = new DataSeedingRunner(null, storeRepository, true);
        runner.run();
    }

    @Test
    void given_itemNames_findByItemsItemNameIn_shouldReturn_correctStores() {
        var results = storeRepository.findByItemsItemNameIn(Arrays.asList("Apple", "Orange", "Grapes"));
        assertThat(results)
                .extracting(GroceryStore::getStoreName)
                .containsExactlyInAnyOrder("Green Grocer", "Market Fresh", "City Mart");

    }

    @Test
    void given_itemNames_findStoresWithItems_shouldReturn_correctStores() {
        var results = storeRepository.findStoresWithItems(Arrays.asList("Apple", "Orange", "Grapes"));
        assertThat(results)
                .extracting(GroceryStore::getStoreName)
                .containsExactlyInAnyOrder("Green Grocer", "Market Fresh", "City Mart");

    }

    @Test
    void given_itemNames_findStoreNamesWithItems_shouldReturn_correctStores() {
        var results = storeRepository.findStoreNamesWithItems(Arrays.asList("Apple", "Orange", "Grapes"));
        System.out.println(results.toString());
        assertThat(results)
                .containsExactlyInAnyOrder("Green Grocer", "Market Fresh", "City Mart");

    }
}
