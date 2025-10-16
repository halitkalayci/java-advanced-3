# Single-Stage

# Multi-Stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY pom.xml ./

RUN mvn -DskipTests dependency:go-offline

COPY src ./src

RUN mvn -DskipTests clean package

FROM eclipse-temurin:21-jre-alpine

COPY --from=builder /build/target/*.jar /app/app.jar

ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]