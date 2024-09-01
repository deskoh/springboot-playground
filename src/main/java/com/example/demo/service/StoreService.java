package com.example.demo.service;

import com.example.demo.model.GroceryStore;
import com.example.demo.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<GroceryStore> getAllStores() {
        return storeRepository.findAll();
    }
}
