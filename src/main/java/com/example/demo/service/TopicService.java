package com.example.demo.service;

import com.example.demo.model.Topic;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

    private final List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("spring", "Spring", "Spring Description"),
            new Topic("java", "Java", "Java Description"),
            new Topic("javascript", "JavaScript", "JavaScript Description")));

    public List<Topic> getAllTopics() {
        return topics;
    }

    public Topic getTopic(String id) {
        var topic = topics.stream().filter(t -> t.id().equals(id)).findFirst();
        if (topic.isPresent())
            return topic.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found");
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public boolean updateTopic(String id, Topic topic) {
        for (var i = 0; i < topics.size(); i++) {
            if (topics.get(i).id().equals(id)) {
                topics.set(i, topic);
                return true;
            }
        }
        return false;
    }

    public void deleteTopic(String id) {
        topics.removeIf(t -> t.id().equals(id));
    }
}
