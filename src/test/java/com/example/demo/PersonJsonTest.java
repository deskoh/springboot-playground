package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class PersonJsonTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JacksonTester<Person> json;

    @Test
    public void testSerialize() throws Exception {
        // Given
        Person person = new Person("John Doe", 30);

        // When
        String json = objectMapper.writeValueAsString(person);

        // Then
        assertThat(json).isEqualTo("{\"name\":\"John Doe\",\"age\":30}");
    }

    @Test
    public void testSerialize2() throws Exception {
        // Given
        Person person = new Person("John Doe", 30);

        // When
        var json = this.json.write(person);

        // Then

        // Assert against a `.json` file in the same package as the test
        assertThat(json).isEqualToJson("expected.json");

        // Or use JSON path based assertions
        assertThat(json).hasJsonPathStringValue("@.name");
        assertThat(json).extractingJsonPathStringValue("@.name").isEqualTo("John Doe");
        assertThat(json).hasJsonPathNumberValue("$.age");
        assertThat(json).extractingJsonPathNumberValue("$.age").isEqualTo(30);
    }

    @Test
    public void testDeserialize() throws Exception {
        // Given
        String json = "{\"name\":\"John Doe\",\"age\":30}";

        // When
        Person person = objectMapper.readValue(json, Person.class);

        // Then
        assertThat(person.getName()).isEqualTo("John Doe");
        assertThat(person.getAge()).isEqualTo(30);
    }
}
