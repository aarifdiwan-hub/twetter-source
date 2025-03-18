package com.test.twt.source.service;

import com.test.twt.source.entity.TweetEntity;
import com.test.twt.source.model.Tweet;
import com.test.twt.source.repository.TweetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TweetProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(TweetProcessingService.class);
    
    private final TweetRepository tweetRepository;
    private final TwitterCacheService cacheService;

    public TweetProcessingService(TweetRepository tweetRepository, TwitterCacheService cacheService) {
        this.tweetRepository = tweetRepository;
        this.cacheService = cacheService;
    }

    @Transactional
    public void processTweet(Tweet tweet) {
        try {
            // Filter: Only process tweets with URLs
            if (tweet.entities() == null || tweet.entities().urls().isEmpty()) {
                logger.debug("Skipping tweet {} - no URLs", tweet.id());
                return;
            }

            // Create entity
            TweetEntity entity = new TweetEntity();
            entity.setId(tweet.id());
            entity.setCreatedDate(tweet.getCreatedAt());
            entity.setAuthorName(tweet.author());
            entity.setUrl(tweet.entities().urls().get(0).url());

            // Save to database
            tweetRepository.save(entity);

            // Cache the tweet ID
            cacheService.cacheTweet(tweet);

            logger.info("Successfully processed tweet {}", tweet.id());
        } catch (Exception e) {
            logger.error("Failed to process tweet: {}", tweet.id(), e);
            throw e;
        }
    }
}