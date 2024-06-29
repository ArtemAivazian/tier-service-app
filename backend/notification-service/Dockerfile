FROM openjdk:17-alpine
COPY target/*.jar ./notification-service.jar
ENTRYPOINT ["java", "-jar", "notification-service.jar"]