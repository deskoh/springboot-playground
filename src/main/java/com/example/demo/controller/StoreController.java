package com.example.demo.controller;

import com.example.demo.model.GroceryStore;
import com.example.demo.service.StoreService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class StoreController {
    StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/all")
    public List<GroceryStore> getStores() {
        return this.storeService.getAllStores();
    }
}
