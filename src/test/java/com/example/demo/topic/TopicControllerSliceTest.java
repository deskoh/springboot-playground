package com.example.demo.topic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicController.class)
public class TopicControllerSliceTest {

    @MockBean
    private TopicService topicService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUpdateTopic() throws Exception {
        given(topicService.updateTopic(eq("123"), any())).willReturn(true);

        this.mockMvc.perform(put("/topics/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":123,\"name\":\"value\",\"description\":\"n.a.\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("value"))
                .andExpect(jsonPath("$.description").value("n.a."));

        given(topicService.updateTopic(any(), any())).willReturn(false);
        this.mockMvc.perform(put("/topics/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":123,\"name\":\"value\",\"description\":\"n.a.\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
