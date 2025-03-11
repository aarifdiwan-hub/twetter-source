package com.test.twt.source.service;

import com.test.twt.source.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TweetProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(TweetProcessingService.class);

    public void processTweet(Tweet tweet) {
        try {
            logger.info("Processing tweet - ID: {}, Created: {}, Text: '{}'",
                tweet.id(),
                tweet.getCreatedAt(),
                tweet.text());
            
            // Add your processing logic here
            // For example:
            // - Store in database
            // - Send to another service
            // - Perform analysis
            // - etc.
            
        } catch (Exception e) {
            logger.error("Failed to process tweet: {}", tweet.id(), e);
            throw e;
        }
    }
}