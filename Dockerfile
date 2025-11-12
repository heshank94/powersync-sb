# Use an official OpenJDK image
FROM eclipse-temurin:21-jdk-jammy

# Set work directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/powersync-java-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
