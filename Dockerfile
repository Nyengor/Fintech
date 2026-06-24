# Use Java image
FROM eclipse-temurin:17-jdk

# Where the app lives inside container
WORKDIR /app

# Copy jar into container
COPY target/*.jar app.jar

# Run Spring Boot
ENTRYPOINT ["java","-jar","app.jar"]