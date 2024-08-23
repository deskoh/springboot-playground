package com.example.demo.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class Pojo3 {
    @NonNull
    private List<Integer> integers = new ArrayList<>();
}
