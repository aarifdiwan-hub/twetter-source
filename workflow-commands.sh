# Initial setup
git clone <your-repo>
cd tweeter-source

# Start services
docker-compose up -d

# Wait for services to be healthy (check logs)
docker-compose logs -f

# Verify PostgreSQL
docker exec tweeter-postgres pg_isready -U tweeter -d tweeterdb

# Verify Kafka
docker exec broker kafka-topics.sh --bootstrap-server broker:29092 --list

# Access Kafka UI
echo "Open http://localhost:8080 in your browser"

# When done developing
docker-compose down

# Complete cleanup (including volumes)
docker-compose down -v
docker system prune