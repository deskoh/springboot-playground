package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class PojoTest {

    @Test
    public void pojo1_constructor_and_setter() {
        var pojo = new Pojo1("john", "doe");
        pojo.setDescription("test");

        assertThat(pojo.getFirstName()).isEqualTo("john");
        assertThat(pojo.getLastName()).isEqualTo("doe");
        assertThat(pojo.getDescription()).isEqualTo("test");
        assertThat(pojo.toString()).isEqualTo("Pojo1(firstName=john, lastName=doe, description=test)");
    }

    @Test
    public void pojo1_non_null_check() {
        assertThatNullPointerException().isThrownBy(() -> {
            new Pojo1("john", null);
        });
    }

    @Test
    public void pojo2_constructor_and_setter() {
        var pojo = new Pojo2("john", "doe");
        pojo.setDescription("test");

        assertThat(pojo.getFirstName()).isEqualTo("john");
        assertThat(pojo.getLastName()).isEqualTo("doe");
        assertThat(pojo.getDescription()).isEqualTo("test");
        assertThat(pojo.toString()).isEqualTo("Pojo2(firstName=john, lastName=doe, description=test)");
    }

    @Test
    public void pojo2_non_null_check() {
        assertThatNullPointerException().isThrownBy(() -> {
            new Pojo2("john", null);
        });
    }

    @Test
    public void pojo3_list_empty_when_initialized() {
        var pojo = new Pojo3();
        assertThat(pojo.getIntegers().size()).isEqualTo(0);
    }

    @Test
    public void pojo3_throws_exception_when_list_set_to_null() {
        var pojo = new Pojo3();
        assertThatNullPointerException().isThrownBy(() -> {
            pojo.setIntegers(null);
        });
    }
}
