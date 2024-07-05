FROM maven:3.8.1-openjdk-17-slim AS maven
WORKDIR /app
COPY ../pom.xml .
COPY ../ms-payments/ ./ms-payments/
WORKDIR /app/ms-payments
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

ARG SERVER_PORT=8080

COPY --from=maven */target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar

EXPOSE ${SERVER_PORT}