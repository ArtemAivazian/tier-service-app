FROM openjdk:17-alpine
COPY target/*.jar ./auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]