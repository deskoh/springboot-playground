package com.example.demo.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/topics/{id}")
    public Topic getMethodName(@PathVariable String id) {
        return topicService.getTopic(id);
    }

    @PostMapping("/topics")
    public ResponseEntity addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable String id, @RequestBody Topic topic) {
        if (topicService.updateTopic(id, topic))
            return ResponseEntity.ok(topic);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/topics/{id}")
    public void deleteTopic(@PathVariable String id) {
        topicService.deleteTopic(id);
    }

}
