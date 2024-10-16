package com.example.demo.service;

import com.example.demo.config.ExtSysConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest({ExternalRestClientService.class, ExtSysConfig.class})
public class ExternalRestClientServiceTest {
    @Autowired
    private ExternalRestClientService service;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void shouldReturnDataFromExternalService() {
        this.server.expect(requestTo("http://localhost:8080/api/hello"))
                .andRespond(withSuccess("Mocked Response", MediaType.TEXT_PLAIN));

        // Call the method under test
        var result = service.getData();

        // Verify the result
        assertEquals("Mocked Response", result);
    }
}
