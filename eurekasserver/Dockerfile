FROM maven:3.9.6 as build
LABEL authors="natri" maintainer="natri" description="Eureka Server" version="1.0"
WORKDIR /app
COPY . .
RUN ls /app/target
RUN mvn clean package -DskipTests


FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
