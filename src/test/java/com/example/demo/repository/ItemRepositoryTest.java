package com.example.demo.repository;

import com.example.demo.config.JpaPopulator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({JpaPopulator.class})
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeAll
    public void setUp() {
        var runner = new DataSeedingRunner(itemRepository, null, true);
        runner.run();
    }

    @Test
    void findItemByName() {
        var item = itemRepository.findItemByName("Whole Wheat Biscuit");
        assertNotNull(item);
        assertEquals("Whole Wheat Biscuit", item.getName());
    }

    @Test
    void existsItemByName() {
        var exists = itemRepository.existsItemByName("Whole Wheat Biscuit");
        assertTrue(exists);
        exists = itemRepository.existsItemByName("Whole Wheat Biscuit!!");
        assertFalse(exists);
    }

    @Test
    void countItemByName() {
        var name = "Healthy Pearl Millet";
        var count = itemRepository.countItemByName(name);
        assertEquals(1, count);
    }

    @Test
    void countItemByCategory() {
        var category = "munchies";
        var count = itemRepository.countItemByCategory(category);
        assertEquals(2, count);
    }

    @Test
    void findAll() {
        var category = "munchies";
        var results = itemRepository.findAll(category);

        assertEquals(2, results.size());
        assertEquals(category, results.get(0).getCategory());
        assertEquals(category, results.get(1).getCategory());
    }

    @Test
    void count() {
        var results = itemRepository.count();
        assertEquals(5, results);
    }

    @Test
    void countTotalQuantity() {
        var count = itemRepository.countTotalQuantity();
        assertEquals(16, count);
    }

    @Test
    void countTotalQuantityByCategory() {
        var category = "munchies";
        var count = itemRepository.countTotalQuantityByCategory(category);
        assertEquals(11, count);
    }
}
