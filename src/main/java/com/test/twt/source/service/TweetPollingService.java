package com.test.twt.source.service;

import com.test.twt.source.model.Tweet;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TweetPollingService {
    private static final Logger logger = LoggerFactory.getLogger(TweetPollingService.class);
    
    private final KafkaConsumer<String, Tweet> consumer;
    private final TweetProcessingService processingService;
    private final String topicName;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public TweetPollingService(
            KafkaConsumer<String, Tweet> consumer,
            TweetProcessingService processingService,
            @Value("${kafka.topic.tweeter-source:tweeter-source}") String topicName) {
        this.consumer = consumer;
        this.processingService = processingService;
        this.topicName = topicName;
    }

    @PostConstruct
    public void init() {
        consumer.subscribe(Collections.singletonList(topicName));
        logger.info("Subscribed to topic: {}", topicName);
    }

    @PreDestroy
    public void cleanup() {
        running.set(false);
        consumer.close();
        logger.info("Kafka consumer closed");
    }

    @Scheduled(fixedDelayString = "${kafka.polling.interval:5000}")
    public void pollTweets() {
        if (!running.get()) {
            return;
        }

        try {
            ConsumerRecords<String, Tweet> records = consumer.poll(Duration.ofMillis(1000));
            if (records.isEmpty()) {
                logger.debug("No new records found");
                return;
            }

            logger.info("Fetched {} records", records.count());
            
            for (ConsumerRecord<String, Tweet> record : records) {
                try {
                    Tweet tweet = record.value();
                    logger.debug("Processing tweet from partition={}, offset={}, id={}",
                        record.partition(), record.offset(), tweet.id());
                    
                    processingService.processTweet(tweet);
                } catch (Exception e) {
                    logger.error("Error processing tweet from offset {}", record.offset(), e);
                }
            }

            consumer.commitAsync((offsets, exception) -> {
                if (exception != null) {
                    logger.error("Commit failed for offsets {}", offsets, exception);
                } else {
                    logger.debug("Commit successful for offsets {}", offsets);
                }
            });

        } catch (Exception e) {
            logger.error("Error during polling", e);
        }
    }
}