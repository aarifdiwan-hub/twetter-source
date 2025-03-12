package com.test.twt.source.service;

import com.test.twt.source.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class TwitterCacheService {

    private static final Logger logger = LoggerFactory.getLogger(TwitterCacheService.class);
    private static final String TWEET_KEY_PREFIX = "tweet:";
    private static final Duration CACHE_DURATION = Duration.ofDays(7);

    private final RedisTemplate<String, Object> redisTemplate;

    public TwitterCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheTweet(Tweet tweet) {
        try {
            String key = TWEET_KEY_PREFIX + tweet.id();
            redisTemplate.opsForValue().set(key, tweet, CACHE_DURATION);
        } catch (Exception e) {
            logger.error("Failed to cache tweet with id: {}", tweet.id(), e);
            throw e;
        }
    }

    public Tweet getTweet(String tweetId) {
        try {
            String key = TWEET_KEY_PREFIX + tweetId;
            Object tweet = redisTemplate.opsForValue().get(key);
            if (tweet == null) {
                logger.debug("Cache miss for tweet id: {}", tweetId);
                return null;
            }
            return (Tweet) tweet;
        } catch (Exception e) {
            logger.error("Failed to retrieve tweet with id: {}", tweetId, e);
            throw e;
        }
    }

    public void deleteTweet(String tweetId) {
        try {
            String key = TWEET_KEY_PREFIX + tweetId;
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("Failed to delete tweet with id: {}", tweetId, e);
            throw e;
        }
    }

    public void clearExpiredTweets() {
        try {
            redisTemplate.keys(TWEET_KEY_PREFIX + "*").forEach(key -> {
                Long ttl = redisTemplate.getExpire(key, TimeUnit.DAYS);
                if (ttl != null && ttl <= 0) {
                    redisTemplate.delete(key);
                    logger.info("Deleted expired tweet with key: {}", key);
                }
            });
        } catch (Exception e) {
            logger.error("Failed to clear expired tweets", e);
            throw e;
        }
    }
}