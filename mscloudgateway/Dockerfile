FROM maven:3.9.6 as build
LABEL authors="natri" maintainer="natri" description="mscartoes" version="1.0"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar

ARG EUREKA_SERVER=localhost
ARG KEYCLOAK_SERVER=localhost
ARG KEYCLOAK_PORT=8081
ENTRYPOINT ["java", "-jar", "app.jar"]