package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class) // only creates beans relevant to AccountController
public class HelloControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Settings settings;

    @Test
    public void shouldReturnHi() throws Exception {
        given(settings.getHost())
                .willReturn("1234");

        Mockito.when(settings.getHost())
                .thenReturn("1234");

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }
}
