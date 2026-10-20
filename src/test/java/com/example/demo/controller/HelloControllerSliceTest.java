package com.example.demo.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.config.Settings;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.cache.test.autoconfigure.AutoConfigureCache;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class) // only creates beans relevant to HelloController
@AutoConfigureCache(cacheProvider = CacheType.SIMPLE)
public class HelloControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Settings settings;

    @Test
    public void shouldReturnHi() throws Exception {
        given(settings.getHost()).willReturn("1234");

        Mockito.when(settings.getHost()).thenReturn("1234");

        mockMvc.perform(get("/hello")).andExpect(status().isOk());
    }
}
