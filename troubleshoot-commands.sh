# Check container logs
docker logs tweeter-postgres
docker logs broker
docker logs tweeter-kafka-ui

# Access PostgreSQL container
docker exec -it tweeter-postgres psql -U tweeter -d tweeterdb

# Check network connectivity
docker network ls
docker network inspect tweeter-source_default

# View container details
docker inspect tweeter-postgres
docker inspect broker

# Restart all services
docker-compose restart

# Force rebuild containers
docker-compose up -d --force-recreate

# Clean up unused resources
docker system prune