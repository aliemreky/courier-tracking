FROM openjdk:17-ea-slim
COPY target/courier-tracking.jar couriertracking.jar
ENTRYPOINT ["java","-jar","/couriertracking.jar"]