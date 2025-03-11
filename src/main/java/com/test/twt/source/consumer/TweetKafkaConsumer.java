package com.test.twt.source.consumer;

import com.test.twt.source.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TweetKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TweetKafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic.tweeter-source:tweeter-source}")
    public void consume(Tweet tweet) {
        logger.info("Processing tweet - ID: {}, Created: {}, Text: '{}'",
            tweet.id(),
            tweet.getCreatedAt(),
            tweet.text());
    }
}