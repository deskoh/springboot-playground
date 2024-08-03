package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExternalServiceTest {

    @InjectMocks
    private ExternalService externalService;
    @Mock
    private WebClient webClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private ResponseSpec responseSpec;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock the WebClient behavior
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Inject mock class and concrete class for testing
        var config = new ExtSysConfig("http://example.com", 80, "/api", "");
        externalService = new ExternalService(webClient, restTemplate, config);
    }

    @Test
    public void shouldReturnDataFromExternalService() {
        // Mock the response from the WebClient
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Mocked Response"));

        // Call the method under test
        var result = externalService.getData().block();

        // Verify the result
        assertEquals("Mocked Response", result);

        // Verify that the WebClient was called as expected
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(eq("http://example.com:80/api"));
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(String.class);
    }

    @Test
    public void shouldReturnDataFromExternalService2() {
        // Mock the response from the RestTemplate
        when(restTemplate.getForObject("http://example.com:80/api", String.class)).thenReturn("Mocked Response");

        // Call the method under test
        var result = externalService.getData2();

        // Verify the result
        assertEquals("Mocked Response", result);

        // Verify that the WebClient was called as expected
        verify(restTemplate).getForObject("http://example.com:80/api", String.class);
    }
}
