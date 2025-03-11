# Start all services in detached mode (background)
docker-compose up -d

# View running containers and their status
docker-compose ps

# View logs of all services
docker-compose logs

# View logs of a specific service
docker-compose logs postgres
docker-compose logs broker
docker-compose logs kafka-ui

# Follow logs in real-time (add -f flag)
docker-compose logs -f

# Stop all services but keep volumes
docker-compose down

# Stop all services and remove volumes (will delete database data)
docker-compose down -v

# Restart a specific service
docker-compose restart postgres
docker-compose restart broker

# View resource usage
docker-compose top

# Initialize git repository (if not already initialized)
git init

# Add all files to git
git add .

# Create initial commit
git commit -m "Initial commit: Tweeter Source project"

# Create new repository on GitHub first, then add it as remote
git remote add origin https://github.com/YOUR_USERNAME/tweeter-source.git

# Push code to GitHub
git push -u origin main

# If you're using 'master' branch instead of 'main':
# git push -u origin master

# Verify remote
git remote -v
