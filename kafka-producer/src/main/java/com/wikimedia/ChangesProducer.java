package com.wikimedia;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;

@Service
public class ChangesProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = TopicName.TOPICNAME.getTopicName();
    //* private static final Logger LOGGER = LoggerFactory.getLogger(ChangesProducer.class);
    private static final String WIKIMEDIA_URL = "https://stream.wikimedia.org/v2/stream/recentchange";

    public ChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send() throws InterruptedException {

        //*Use event source to read real time stream data from wikimedia */
        EventHandler eventHandler = new ChangesHandler(kafkaTemplate, topic);
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(WIKIMEDIA_URL));
        EventSource source = builder.build();
        source.start();
        
        TimeUnit.MINUTES.sleep(30);
    }
}
