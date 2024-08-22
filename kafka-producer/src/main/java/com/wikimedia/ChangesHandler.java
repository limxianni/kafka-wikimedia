package com.wikimedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

public class ChangesHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangesHandler.class.getName());
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public ChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {
    }

    @Override
    public void onClosed() throws Exception {
    }

    @Override
    public void onMessage(String str, MessageEvent messageEvent) throws Exception {
        LOGGER.info(String.format("Received message -> %s: ", messageEvent.getData()));
        kafkaTemplate.send(topic, str);
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error occurred: ", throwable);
    }

    @Override
    public void onComment(String str) throws Exception {
    }
}
