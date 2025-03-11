package com.test.twt.source.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record Tweet(
    String type,
    String id,
    @JsonProperty("created_timestamp") String createdTimestamp,
    String text
) {
    // Utility method to get timestamp as Instant
    public Instant getCreatedAt() {
        return Instant.ofEpochMilli(Long.parseLong(createdTimestamp));
    }
}