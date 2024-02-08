# Microservice Project using Sring
## Description
This project is a microservice project using Spring Boot, Spring Cloud, netflix eureka.

# communication
 Assync communication using **RABBITMQ**
# empacotando a aplicação
command line starter
```shell
.\mvnw clean package -DskipTests
```
the jar files gonna be in target folder
to run it use

````shell
java -jar "file.jar"
````
## docker
each service has a dockerfile to build the image
```shell
docker build "name" .
```

## creating docker network
````shell
docker network create "creditms-network"
````
to see
````shell
docker network ls
````


to run the image
```shell
docker run --name "name" -p 8080:8080 --network creditms-network "name"
```
to now each por to reference please check the docker file.


running rabbit mq in the network 

```shell
docker run --name creditms-rabbitmq 5672:5672 -p 15672:15672 --network creditms-network rabbitmq:3-management-alpine

```
 open and create the rabbit mq queues "emissao-cartoes"

## Requirements
* Java 17
* Maven 3.9.6
