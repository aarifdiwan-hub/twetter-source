# Tweeter Source

A Spring Boot application that processes and manages tweets using Apache Kafka and PostgreSQL.

## Prerequisites

- Java 17
- Docker and Docker Compose
- Maven (or use included Maven wrapper)

## Technology Stack

- Spring Boot 3.4.3
- Apache Kafka
- PostgreSQL 15
- Docker
- Maven

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

3. Build and run the application:
```bash
./mvnw spring-boot:run
```

## Services

- **PostgreSQL**: Running on port 5432
  - Database: tweeterdb
  - Username: tweeter
  - Password: tweeter123

- **Kafka**: Running on port 9092
  - Bootstrap servers: localhost:9092
  - Internal broker: broker:29092

- **Kafka UI**: Available at http://localhost:8080

- **Application**: Running on port 8081

## Docker Commands

```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Configuration

Main configuration files:
- `src/main/resources/application.yml`: Application configuration
- `docker-compose.yml`: Infrastructure services configuration

## Development

The project uses Spring Boot's dev tools for hot reloading. Make sure to:
1. Enable automatic builds in your IDE
2. Run the application in dev mode

## License

[Add your license information here]