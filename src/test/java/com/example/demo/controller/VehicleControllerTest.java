package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = { VehicleController.class })
public class VehicleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testV1() throws Exception {
        mockMvc.perform(get("/vehicle/v1"))
                .andExpect(jsonPath("$[*].type", hasSize(2)));
    }

    @Test
    public void testV2() throws Exception {
        mockMvc.perform(get("/vehicle/v2"))
                .andExpect(jsonPath("$[*].type", hasSize(2)));
    }

    @Test
    public void testV3() throws Exception {
        mockMvc.perform(get("/vehicle/v3"))
                .andExpect(jsonPath("$[*].type", hasSize(0)));
    }

    @Test
    public void testV4() throws Exception {
        mockMvc.perform(get("/vehicle/v4"))
                .andExpect(jsonPath("$[*].type", hasSize(0)));
    }

    @Test
    public void testV5() throws Exception {
        mockMvc.perform(get("/vehicle/v5"))
                .andExpect(jsonPath("$[*].type", hasSize(2)));
    }
}
