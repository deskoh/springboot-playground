package com.example.demo;

import lombok.*;

@RequiredArgsConstructor
@ToString
public class Pojo2 {
    @Getter
    private final String firstName;
    @NonNull
    @Getter
    private final String lastName;
    @Getter
    @Setter
    private String description;
}