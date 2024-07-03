# Semester Project Requirements

## Submission at the End of the Semester

### Common Requirements

- **Select appropriate technology and language:** Java/SpringBoot, Java, C#, etc. (mandatory)
  - *SpringBoot (Java) (Backend)*
- **README in Git with a description of what is completed and where the functionality is located** (mandatory)
  - *Completed*
- **Use a common database (relational or graph-based)** (mandatory)
  - *PostgreSQL 15*
  - Configurations:
    - `application.yml`
    - `application-docker.yml`
    - `application-production.yml`
    - `docker-compose.yml`
- **Utilize cache (e.g., Hazelcast)** (optional)
  - *Hazelcast*
    - Classes:
      - `ProductsServiceImpl.java`
      - `StockServiceImpl.java`
      - `OrderServiceImpl.java`
      - `UserServiceImpl.java`
    - Configurations:
      - `hazelcast.yml`
      - `hazelcast-docker.yml`
      - `hazelcast-production.yml`
- **Use messaging principles (Kafka or JMS)** (optional)
  - *Kafka*
    - Configurations:
      - `application.yml`
      - `application-docker.yml`
      - `application-production.yml`
      - `docker-compose.yml`
    - Classes:
      - `OrderPlacedEvent.java`
      - `EventHandler.java`
      - `KafkaTopicConfig.java`
      - `OrderPlacedEventHandler.java`
- **The application must be secured with either basic authorization or OAuth2** (optional)
  - *JWT Token*
    - Classes:
      - `AuthorizationFilter.java`
      - `JwtService.java`
      - `SecurityConfiguration.java`
      - `AuthenticationFilter.java`
      - `AuthServiceImpl.java`
- **Use one of the following technologies: SOAP, REST, GraphQL, Java RMI, Corba, XML-RPC** (optional)
  - *REST*
- **Deploy on a production server, e.g., Heroku** (mandatory)
  - *AWS EC2 instances*
  - *Deployment Diagram: vz*
- **Select appropriate architecture (event-based, pipe and filter, etc.)** (mandatory)
  - *Event-based: Product-Service, Order-Service, Notification-Service*
- **Initialization procedure (how to deploy the application, where the basic data for a new DB like admin are located)** (mandatory)
  - *Completed*
- **Utilize Elasticsearch** (optional)
  - Goal: Logs Aggregation
  - Configurations:
    - *Logstash*
      - `logstash.conf`
      - `docker-compose.yml`
      - *Deployment Diagram: vz*
    - *Elasticsearch*
      - `docker-compose.yml`
      - *Deployment Diagram: vz*
    - *Kibana*
      - `docker-compose.yml`
      - *Deployment Diagram: vz*
- **Use at least 5 design patterns (must make sense)** (mandatory)
  - *DAO*
    - Classes:
      - `OrderRepository.java`
      - `AuthRepository.java`
      - `ProductRepository.java`
      - `StockRepository.java`
      - `UserRepository.java`
  - *DI (Dependency Injection)*
    - Classes:
      - `UserController.java`
      - `UserServiceImpl.java`
      - `ProductsController.java`
      - `StocksController.java`
      - `ProductsServiceImpl.java`
      - `StockServiceImpl.java`
      - `OrderServiceImpl.java`
      - `OrderController.java`
      - `AuthServiceImpl.java`
      - `AuthenticationFilter.java`
      - `AuthorizationFilter.java`
      - `SecurityConfiguration.java`
      - `JwtService.java`
  - *Singleton*
    - Classes:
      - `AuthServiceApplication.java`
      - `ProductServiceApplication.java`
      - `UserServiceApplication.java`
      - `OrderServiceApplication.java`
  - *DTO*
    - Classes:
      - `ProductDto.java`
      - `ProductLdo.java`
      - `StockDto.java`
      - `StockLdo.java`
      - `UserDto.java`
      - `UserLdo.java`
      - `OrderDto.java`
      - `OrderedProductDto.java`
  - *Builder (Spring Annotation - mapper/ in product-service)*
    - Classes:
      - `UserEntity.java`
      - `Order.java`
      - `OrderedProduct.java`
      - `Product.java`
      - `Stock.java`
      - `Mapper.java`
  - *Interceptor*
    - Classes:
      - `LoggingFilter.java`
- **For each team member, provide 2 use cases (to ensure the software is not trivial)** (mandatory)
  - *Completed*

### Bonus Points

- **Cloud services (Azure, AWS)** +2 points
  - *AWS EC2 for deploying*

### Additional Requirements

- **Tests**
  - `OrderServiceApplicationTests.java`
  - `ProductServiceApplicationTests.java`
  - `UserServiceApplicationTests.java`
  - `JwtUtil.java`
- **Actuator**
  - `application.yml`
- **Docker, Docker-compose**
  - `Dockerfile`
  - `Dockerfile-production`
  - `docker-compose.yml`
- **Eureka Discovery Server**
  - `discovery-service/`
- **Different Environments**
  - *Dev, Prod*
    - `application-docker.yml`
    - `application-production.yml`

### What is not working

- **Flyway**
  - *Does not work for common database*
  - *User-service successfully migrates, but others do not*

