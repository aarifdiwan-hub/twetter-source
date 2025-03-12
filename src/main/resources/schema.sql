CREATE TABLE IF NOT EXISTS tweets (
    id VARCHAR(255) PRIMARY KEY,
    created_date TIMESTAMP NOT NULL,
    author_name VARCHAR(255),
    url VARCHAR(2048)
);

CREATE INDEX IF NOT EXISTS idx_tweets_created_date ON tweets(created_date);