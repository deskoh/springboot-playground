package com.example.demo;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Builder
@ToString
@Setter
@Getter
public class Pojo4 {
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
    @Singular
    private final List<String> hobbies;
    @Builder.Default
    private int age = -1;
    private String address;
    @Builder.Default
    private long created = System.currentTimeMillis();

    public static void main(String[] args) {
        Pojo4 person = Pojo4.builder()
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .address("123 Main St")
                .hobbies(Arrays.asList("walking", "running"))
                .hobby("cycling")
                .hobby("swimming")
                .build();
        System.out.println(person);
    }
}