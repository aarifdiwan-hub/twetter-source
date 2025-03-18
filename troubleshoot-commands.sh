# Check PostgreSQL container logs
docker logs tweeter-postgres

# Check if PostgreSQL is ready
docker exec tweeter-postgres pg_isready -U tweeter -d tweeterdb

# Verify PostgreSQL connection details
docker inspect tweeter-postgres | grep IPAddress

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
