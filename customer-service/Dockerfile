# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY customer-service/pom.xml customer-service/
COPY customer-service/src customer-service/src
RUN mvn -f customer-service/pom.xml clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/customer-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
