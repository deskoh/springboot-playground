package com.example.demo;

import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicController;
import com.example.demo.topic.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TopicController.class)
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    @Test
    public void getTopics_shouldReturnTopic() throws Exception {
        // arrange
        given(topicService.getTopic("1"))
                .willReturn(
                        new Topic("spring", "Spring", "Spring Description")
                );

        // act and assert
        mockMvc.perform(get("/topics/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("spring"))
                .andExpect(jsonPath("$.name").value("Spring"))
                .andExpect(jsonPath("$.description").value("Spring Description"));

        // verify
        verify(topicService).getTopic("1");

    }
}
