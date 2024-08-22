package com.wikimedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.wikimedia.entity.WikimediaData;
import com.wikimedia.repository.DataRepository;

@Service
public class DatabaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConsumer.class);
    private final DataRepository dataRepository;

    public DatabaseConsumer(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "wikimedia-topic", groupId = "group-consumer")
    public void consume(String message) {
        LOGGER.info(String.format("Event message received -> %s", message));
        
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setData(message);

        dataRepository.save(wikimediaData);
    }
}
