package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// Test without using @RestClientTest annotation as ExtSysConfig needs to be constructed manually
public class ExternalRestClientServiceTest {
    private ExternalRestClientService service;

    private MockRestServiceServer server;

    @BeforeEach
    public void setUp() {
        RestClient.Builder restClientBuilder = RestClient.builder();
        this.server = MockRestServiceServer.bindTo(restClientBuilder).build();
        var config = new ExtSysConfig("http://example.com", 80, "/api", "");
        service = new ExternalRestClientService(restClientBuilder, config);
    }

    @Test
    public void shouldReturnDataFromExternalService() {
        this.server.expect(requestTo("http://example.com:80/api"))
                .andRespond(withSuccess("Mocked Response", MediaType.TEXT_PLAIN));

        // Call the method under test
        var result = service.getData();

        // Verify the result
        assertEquals("Mocked Response", result);
    }
}
