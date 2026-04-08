# 🎬 Chill Clips Backend API

**By Oluwasheyi Ojelade | Virusinferno Digital Studio** *Cloud • DevOps • AI Automation*



## 📌 Overview
This repository contains the backend service for the **Chill Clips** streaming application. It is a robust, containerized REST API built using **Java and Spring Boot**, securely connected to a **MongoDB Atlas** cloud database, and fully automated for continuous delivery.

## 🚀 Tech Stack
* **Framework:** Java 17 / Spring Boot 3 / Spring Security
* **Database:** MongoDB Atlas (Cloud)
* **Authentication:** JWT (JSON Web Tokens)
* **DevOps:** Docker, AWS ECR (Elastic Container Registry), GitHub Actions
* **Deployment:** Contabo VPS (Linux), Dockerized Caddy Reverse Proxy

## 🛡️ Security & Incident Response
This architecture was specifically hardened following a threat incident:
* **Cloud Database Migration:** Migrated the data layer from a locally exposed instance to a fully managed, secured MongoDB Atlas cluster to mitigate automated ransomware bots.
* **Secrets Management:** Implemented GitHub Secrets to handle AWS credentials, SSH keys, and MongoDB connection strings, ensuring zero credential exposure in the codebase.

## ⚙️ CI/CD Pipeline Architecture
This project features a 100% automated Continuous Integration and Continuous Deployment (CI/CD) pipeline:
1. **Build:** Code pushed to `main` triggers GitHub Actions to compile the Java application.
2. **Containerize:** The pipeline builds a Docker image and pushes it to **AWS ECR**.
3. **Deploy:** Using secure `ed25519` SSH keys, GitHub Actions connects directly to the production Linux VPS and executes a Bash script to seamlessly pull the new image and restart the Docker container with zero manual intervention.

## 🛠️ Local Development
To run this API locally, you must configure your `application.properties` with the following variables:
```properties
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster-url>
jwt.secret=<your-secure-secret-key>