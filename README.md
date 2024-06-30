# Semester Project Requirements

## Submission at the End of the Semester

### Common Requirements:
- **Select appropriate technology and language:** Java/SpringBoot, Java, C#, etc. (mandatory)
  - *SpringBoot (Backend)*
- **README in Git with a description of what is completed and where the functionality is located** (mandatory)
  - *Completed*
- **Use a common database (relational or graph-based)** (mandatory)
  - *PostgreSQL*
  - *Prod: docker container on EC2 instance*
  - *Dev*: vz. docker-compose.yml 
- **Utilize cache (e.g., Hazelcast)** (optional)
  - *Hazelcast*
  - *Spring Cache Annotations in user-service, product-service and order-service on the Service Layer*
  - *Dev: application-docker.yml*
  - *Prod: application-production.yml*
- **Use messaging principles (Kafka or JMS)** (optional)
  - *Kafka*
  - *Dev: vz. docker-compose.yml*
  - *Prod: EC2 instance with Kafka broker and Zookeeper in docker containers*
- **The application must be secured with either basic authorization or OAuth2** (optional)
  - *JWT Token with ADMIN-USER roles: config/ package in auth-service, user-service, product-servivce, order-service*
- **Use of interceptors (at least one class), e.g., for logging (log the incoming request)** (optional)
  - *Api-Gateway: logging incoming requests*
- **Use one of the following technologies: SOAP, REST, GraphQL, Java RMI, Corba, XML-RPC** (optional)
  - *REST*
- **Deploy on a production server, e.g., Heroku** (mandatory)
  - *AWS EC2 instances: vz Deployment Diagram*
- **Select appropriate architecture (event-based, pipe and filter, etc.)** (mandatory)
  - *event-based: Product-Service, Order-Service, Notification-Service*
- **Initialization procedure (how to deploy the application, where the basic data for a new DB like admin are located)** (mandatory)
  - *Completed*
- **Utilize Elasticsearch** (optional)
  - *Using ELK stack*
  - *Dev: logstash-docker/ dir with logstash.conf for each microservice; Elasticsearch, Kibana and Logstash running in docker containers - vz docker-compose file
- **Use at least 5 design patterns (must make sense)** (mandatory)
  - *DAO*
  - *Singleton: in main classes of microservices - reusing objects*
  - *DTO, LDO in between Presentation, Business and Data Access Layers*
  - *Builder - Spring Annotation*
  - Interceptor - in api-gateway
- **For each team member, provide 2 use cases (to ensure the software is not trivial)** (mandatory)
  - *Completed*

### Bonus Points:
- **Cloud services (Azure, AWS)** +2 points
  - *AWS EC2 for deploying*

### Also:
- **Actuator**
  - *vz. application.yml in all microservices* 
- **Docker, Docker-compose**
- **Eureka Discovery Server**
  - vz. discovery-service
- **Different Environments**
  - *Dev, Prod*
  - vz. application-docker.yml and application-production.yml

### What is not working:
- **Flyway**
  - *Does not work for common database*
  - *User-service succsessfully mirgates, but others do not*

  
