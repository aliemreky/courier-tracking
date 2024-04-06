# Courier Tracking System
In this project, there is a web application restful with Java, it mainly takes the flow geographical locations of the couriers (time, courier, latitude, longitude) as input and using these inputs, the total travelling distance of the courier is obtained. 



## Project Tech and Dependencies
* Java 17
* Spring Boot 3.2.4
* Spring Cache
* JPA
* H2 Database
* JUnit
* Mockito
* Log4j
* Docker Compose

## How to Start the Project
```shell
mvn clean package
docker compose up couriertracking
```

It will be started on port 8080.

## Postman Collection

The collection file (Courier Tracking.postman_collection.json) is under the main directory.

## Test Run
    
* mvn test => Run All Test
* mvn clean install -Dit => Clears the target directory and builds the project and installs the resulting artifact (JAR) with unit and integration tests

## Test Coverage
Project :
* Class : %80
* Method : %75
* Line : %80
