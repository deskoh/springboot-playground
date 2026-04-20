package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HelloControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getHello_shouldReturnHi() {
        var url = "/hello";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getBody()).isEqualTo("Hi");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void notFound_shouldBeJson() {
        var url = "/invalidUrl";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }
}
