package com.test.twt.source.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;

public record Tweet(
    String type,
    String id,
    String title,
    String text,
    String author,
    @JsonProperty("created_timestamp") Long createdTimestamp,
    @JsonProperty("ingestionDateTime") String ingestionDateTime,
    @JsonProperty("formattedTimestamp") String formattedTimestamp,
    Entities entities
) {
    public record Entities(
        List<UrlEntity> urls
    ) {}

    public record UrlEntity(
        String url,
        @JsonProperty("expanded_url") String expandedUrl,
        @JsonProperty("display_url") String displayUrl,
        List<Integer> indices
    ) {}

    // Utility method to get timestamp as Instant
    public Instant getCreatedAt() {
        return Instant.ofEpochMilli(createdTimestamp);
    }
}