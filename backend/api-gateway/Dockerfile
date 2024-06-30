FROM openjdk:17-alpine
COPY target/*.jar ./api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]