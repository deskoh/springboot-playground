package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource(properties = {
        "logging.level.com.example.demo=DEBUG",
})
public class HelloControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello_shouldReturnHi(CapturedOutput capture) throws Exception {
        mockMvc.perform(get("/hello"))
                // .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void hello_shouldLogDebug(CapturedOutput capture) {
        assertTrue(capture.getOut().contains("this is debug logging"));
        // Using assertj
        assertThat(capture.getOut()).contains("this is debug logging");
    }
}
