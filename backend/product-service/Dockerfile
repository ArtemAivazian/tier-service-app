FROM openjdk:17-alpine
COPY target/*.jar ./product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]