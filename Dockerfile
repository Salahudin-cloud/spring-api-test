# Stage 1: Build the application
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: Run the application
FROM openjdk:21-jdk-oracle
WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=build /app/build/libs/*.jar application.jar

# Run the application
ENTRYPOINT ["java", "-jar", "application.jar"]
