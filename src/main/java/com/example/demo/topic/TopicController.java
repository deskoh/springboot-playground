package com.example.demo.topic;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

  @PutMapping("topics/{id}")
  public Topic updateTopic(@PathVariable String id, @RequestBody Topic topic) {
    topicService.updateTopic(id, topic);
    return topic;
  }

  @DeleteMapping("topics/{id}")
  public void deleteTopic(@PathVariable String id) {
    topicService.deleteTopic(id);
  }

}
