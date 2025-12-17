# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY config-service/pom.xml config-service/
COPY config-service/src config-service/src
RUN mvn -f config-service/pom.xml clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/config-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
