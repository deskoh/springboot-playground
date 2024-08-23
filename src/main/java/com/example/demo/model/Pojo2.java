package com.example.demo.model;

import lombok.*;

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