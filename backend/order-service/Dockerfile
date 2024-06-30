FROM openjdk:17-alpine
COPY target/*.jar ./order-service.jar
ENTRYPOINT ["java", "-jar", "order-service.jar"]