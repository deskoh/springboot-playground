package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(controllers = { VehicleController.class })
@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void test200() throws Exception {
        var result = mockMvc.perform(get("/exception/200"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResolvedException()).isNull();
    }

    @Test
    public void test401() throws Exception {
        // getResolvedException will be null
        var result = mockMvc.perform(get("/exception/401"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void test409() throws Exception {
        var ex = (ResponseStatusException) mockMvc.perform(get("/exception/409"))
                .andExpect(status().isConflict())
                .andReturn()
                .getResolvedException();
        // ex.getMessage will be '409 CONFLICT "conflict message"'
        assertThat(ex.getReason()).isEqualTo("conflict message");
    }

    @Test
    public void test403() throws Exception {
        var result = mockMvc.perform(get("/exception/403"))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(result).isEqualTo("");
    }

    @Test
    public void test404() throws Exception {
        var ex = mockMvc.perform(get("/exception/404"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException();
        assertThat(ex.getMessage()).isEqualTo("custom not found message");
    }

    @Test
    public void test400() throws Exception {
        var ex = mockMvc.perform(get("/exception/404"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException();
        assertThat(ex.getMessage()).isEqualTo("custom bad request message");
    }

    @Test
    public void testException() throws Exception {
        // ResponseCode will follow RestControllerAdvice
        var ex = mockMvc.perform(get("/exception/exception"))
                .andExpect(status().isInternalServerError())
                .andReturn()
                .getResolvedException();
        assertThat(ex.getMessage()).isEqualTo("exception handled by RestControllerAdvice");
    }


}
