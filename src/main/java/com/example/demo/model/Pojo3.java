package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class Pojo3 {
    @NonNull
    private List<Integer> integers = new ArrayList<>();
}
