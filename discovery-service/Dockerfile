# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Copy parent pom
COPY pom.xml .
# Copy service pom
COPY discovery-service/pom.xml discovery-service/
# Copy service source
COPY discovery-service/src discovery-service/src
# Build service
RUN mvn -f discovery-service/pom.xml clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/discovery-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
