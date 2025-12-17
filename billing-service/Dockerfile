# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY billing-service/pom.xml billing-service/
COPY billing-service/src billing-service/src
RUN mvn -f billing-service/pom.xml clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/billing-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
