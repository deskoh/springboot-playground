package com.example.demo.repository;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.example.demo.repository")
public class MongoConfig {
}
