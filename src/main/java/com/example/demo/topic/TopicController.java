package com.example.demo.topic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/topics/{id}")
    public Topic getMethodName(@PathVariable String id) {
        return topicService.getTopic(id);
    }

    @PostMapping("/topics")
    public ResponseEntity<HttpStatus> addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<?> updateTopic(@PathVariable String id, @RequestBody Topic topic) {
        if (topicService.updateTopic(id, topic))
            return ResponseEntity.ok(topic);
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/topics/{id}")
    public void deleteTopic(@PathVariable String id) {
        topicService.deleteTopic(id);
    }

}
