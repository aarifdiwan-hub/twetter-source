package com.test.twt.source.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TweetTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDeserializeFromJson() throws Exception {
        String json = """
            {
              "type": "tweet",
              "id": "1234858592",
              "created_timestamp": "1392078023603",
              "text": "Hello World!"
            }
            """;

        Tweet tweet = objectMapper.readValue(json, Tweet.class);

        assertEquals("tweet", tweet.type());
        assertEquals("1234858592", tweet.id());
        assertEquals("1392078023603", tweet.createdTimestamp());
        assertEquals("Hello World!", tweet.text());
        assertEquals(Instant.ofEpochMilli(1392078023603L), tweet.getCreatedAt());
    }
}