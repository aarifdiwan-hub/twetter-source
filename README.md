# Tweeter Source

A Spring Boot application that processes tweets using Apache Camel, Kafka, and PostgreSQL. The application demonstrates a robust event-driven architecture for processing social media data.

## Architecture Overview

- **Apache Camel**: Routes and processes incoming tweets
- **PostgreSQL**: Persistent storage for processed tweets
- **Spring Boot**: Application framework and dependency management

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven (or use included Maven wrapper)

## Quick Start

1. Clone the repository:
```bash
git clone https://github.com/jteche/tweeter-source.git
cd tweeter-source
```

2. Start the infrastructure services:
```bash
docker-compose up -d
```

3. Wait for services to be healthy:
```bash
docker-compose ps
```

4. Build and run the application:
```bash
./mvnw spring-boot:run
```

## Infrastructure Services

### PostgreSQL
- **Port**: 5432
- **Database**: tweeterdb
- **Username**: tweeter
- **Password**: tweeter123
- **Health Check**: `docker exec tweeter-postgres pg_isready -U tweeter -d tweeterdb`

### Kafka
- **Bootstrap Servers**: 
  - External: localhost:9092
  - Internal: broker:29092
- **Default Topic**: twitter-feed-topic
- **Health Check**: `docker exec broker kafka-topics.sh --bootstrap-server broker:29092 --list`

### Kafka UI
- **URL**: http://localhost:8080
- Provides web interface for Kafka monitoring

## Application Configuration

### Main Configuration Files
- `src/main/resources/application.yml`: Application properties
- `docker-compose.yml`: Infrastructure services
- `pom.xml`: Dependencies and build configuration

### Key Properties
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tweeterdb
    username: tweeter
    password: tweeter123

server:
  port: 8081

kafka:
  topic:
    tweeter-source: twitter-feed-topic
```

## Testing the Application

### 1. Create Sample Tweets
Use the provided script to generate and send sample tweets:
```bash
./scripts/produce-sample-tweets.sh
```

### 2. Monitor Messages
View messages using either:
```bash
# Using console consumer
./scripts/monitor-tweets.sh

# Or using Kafka UI
open http://localhost:8080
```

### 3. Manage Kafka Topics
Use the management script:
```bash
./scripts/manage-kafka-topic.sh [create|delete|list|describe]
```

## Error Handling

The application implements robust error handling:
- Maximum 3 redelivery attempts
- Exponential backoff strategy
- Dead letter channel for failed messages
- Error logging and monitoring

## Development

### Building
```bash
./mvnw clean install
```

### Running Tests
```bash
./mvnw test
```

### Development Mode
The application uses Spring Boot Dev Tools for hot reloading:
1. Enable automatic builds in your IDE
2. Run with `./mvnw spring-boot:run`

## Troubleshooting

### Common Commands
```bash
# Check service logs
docker-compose logs -f

# Restart services
docker-compose restart

# Clean up
docker-compose down -v
docker system prune
```

### Health Checks
```bash
# PostgreSQL
docker exec tweeter-postgres pg_isready -U tweeter -d tweeterdb

# Kafka
docker exec broker kafka-topics.sh --bootstrap-server broker:29092 --list
```

## Project Structure

```
tweeter-source/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/test/twt/source/
│       │       ├── config/
│       │       ├── model/
│       │       ├── route/
│       │       └── service/
│       └── resources/
│           └── application.yml
├── scripts/
│   ├── produce-sample-tweets.sh
│   ├── monitor-tweets.sh
│   └── manage-kafka-topic.sh
└── docker-compose.yml
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

[Add your license information here]