# FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
# COPY . .
# RUN mvn clean package -DskipTests

# FROM openjdk:21-slim
# COPY --from=build /target/chat-app-backend-0.0.1-SNAPSHOT.jar chat-app-backend.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","chat-app-backend.jar"]







# Use Maven for the build process
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

# Copy the project files to the container
COPY . /app

# Set the working directory
WORKDIR /app

# Build the project (skip tests if you want faster builds)
RUN mvn clean package -DskipTests

# Use OpenJDK as the runtime image
FROM openjdk:21-slim

# Copy the jar file from the previous stage
COPY --from=build /app/target/chat-app-backend-0.0.1-SNAPSHOT.jar /chat-app-backend.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/chat-app-backend.jar"]
