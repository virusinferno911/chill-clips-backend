# ── Stage 1: Build the application ──────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

# Install Maven on top of the JDK image
RUN apk add --no-cache maven

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project descriptor first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy all the source code
COPY src ./src

# Copy the application.properties that gets injected by the CI pipeline
COPY src/main/resources/application.properties src/main/resources/application.properties

# Compile and package the application into a JAR file
RUN mvn clean package -DskipTests

# ── Stage 2: Run the application ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy ONLY the compiled JAR from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Document that the application uses port 8080
EXPOSE 8080

# Start the Spring Boot application when the container runs
CMD ["java", "-jar", "app.jar"]