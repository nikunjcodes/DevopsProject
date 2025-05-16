# Build stage
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Download dependencies, run tests and build
RUN mvn clean package -DskipTests
RUN mvn test

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"] 