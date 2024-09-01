package com.example.demo.controller;

import com.example.demo.model.GroceryStore;
import com.example.demo.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
