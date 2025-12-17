# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY inventory-service/pom.xml inventory-service/
COPY inventory-service/src inventory-service/src
RUN mvn -f inventory-service/pom.xml clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/inventory-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
