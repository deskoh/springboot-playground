package com.example.demo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@ToString
public class Pojo3 {
    @NonNull
    @Getter
    @Setter
    private List<Integer> integers = new ArrayList<>();
}
