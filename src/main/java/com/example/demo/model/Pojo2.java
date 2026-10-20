package com.example.demo.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class Pojo2 {
    private final String firstName;

    @NonNull
    private final String lastName;

    @Setter
    private String description;
}
