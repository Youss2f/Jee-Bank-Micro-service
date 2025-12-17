# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY gateway-service/pom.xml gateway-service/
COPY gateway-service/src gateway-service/src
RUN mvn -f gateway-service/pom.xml clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/gateway-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
