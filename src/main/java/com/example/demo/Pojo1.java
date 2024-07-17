package com.example.demo;

import lombok.Data;
import lombok.NonNull;

@Data
public class Pojo1 {
    private final String firstName;

    @NonNull
    private final String lastName;

    private String description;
}
