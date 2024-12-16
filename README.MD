# Docker Usage Guide - Installation, Starting, and Shutting Down Docker

## Description
This guide provides basic steps to install Docker, start/stop Docker on different operating systems, and manage containers in your project.

### Objectives
- Guide to install Docker
- How to start and stop Docker
- Basic commands to manage containers

---

## 1. Introduction to Docker
Docker is a platform for packaging, distributing, and deploying applications in containers. A container holds all resources required to run an application independently, allowing easy portability across different environments.

---

## 2. Docker Installation

### On Windows / macOS
- Download Docker Desktop from the Docker website: https://www.docker.com/get-started
- Install and start Docker Desktop.

### On Linux
sudo apt-get update
sudo apt-get install -y docker.io

#### Verify Docker Installation
docker --version

---

## 3. Start and Stop Docker

### On Windows / macOS
- Start Docker: Open the Docker Desktop application from the application menu.
- Stop Docker: Click on the Docker icon in the system tray and select Quit Docker Desktop.

### On Linux
# Start Docker
sudo systemctl start docker

# Stop Docker
sudo systemctl stop docker

# Restart Docker
sudo systemctl restart docker

---

## 4. Start and Shutdown Current Docker Containers

### To Stop and Remove All Containers
docker stop $(docker ps -aq)      # Stop all running containers
docker rm $(docker ps -aq)        # Remove all containers

### To Stop and Remove Containers Created with Docker Compose
docker-compose down

### To Start Docker Compose Containers in Detached Mode
docker-compose up -d

---

## 5. Basic Docker Commands

### View Running Containers
docker ps

### View All Containers (Including Stopped Containers)
docker ps -a

### Start a Specific Container
docker start <container_id>

### Stop a Specific Container
docker stop <container_id>

### Remove a Container
docker rm <container_id>

---

## 6. Docker Image Management

### List Existing Docker Images
docker images

### Remove a Docker Image
docker rmi <image_id>

---

## 7. Conclusion
Docker simplifies the development, deployment, and management of applications. Mastering Docker helps to develop and deploy applications efficiently.

### Reference
- Official Docker Documentation: https://docs.docker.com/
