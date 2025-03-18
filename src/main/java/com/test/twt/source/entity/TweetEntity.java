package com.test.twt.source.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tweets")
public class TweetEntity {
    @Id
    private String id;
    
    @Column(name = "created_date")
    private Instant createdDate;
    
    @Column(name = "author_name")
    private String authorName;
    
    @Column(name = "url")
    private String url;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Instant getCreatedDate() { return createdDate; }
    public void setCreatedDate(Instant createdDate) { this.createdDate = createdDate; }
    
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}