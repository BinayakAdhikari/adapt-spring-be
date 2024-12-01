# Spring Boot Boilerplate

*Adapt Boilerplate* is designed for ADAPT Application.

## Technologies Used

- **Spring Boot (v2.7.10)**: Simplifies the development of Spring applications.
- **Spring Data JPA**: Provides easy integration with relational databases using JPA.
- **Spring Validation**: Handles validation of Java Beans.
- **Spring Security + JWT Token**: Secures your application with JWT-based authentication.
- **PostgreSQL**: A powerful, open source object-relational database system.
- **MapStruct**: A code generator that simplifies the mapping of Java Beans.
- **Lombok**: Reduces boilerplate code for model objects by generating getters, setters, and other methods.
- **Swagger (Open API)**: Provides interactive API documentation.

## Project Structure

The project follows a standard Maven project layout:

```sh
spring-boot-boilerplate
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/adapt/boilerplate/springboot
│   │   │       ├── configuration
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── exception
│   │   │       ├── mapper
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── security
│   │   │       ├── service
│   │   │       ├── utils
│   │   │       └── SpringBootBoilerplateApplication.java
│   │   └── resources
│   │       ├── application.yml
│   │       └── static
│   └── test
│       └── java
│           └── com/adapt/boilerplate/springboot
└── pom.xml
```

## Running the Application
### Prerequisites
 - Docker: Make sure Docker is installed and running.
 - Java: Ensure you have Java 11 or higher installed.
 - Maven: Make sure Maven is installed.

### Deploy with docker
If you're using Docker, you can use the following command to start the containers in detached mode:

```sh
docker-compose up -d
```

## Build the Project
Navigate to the root of the project and run the following command to build the project:

```sh
mvn clean install
```

## Run the Application
Navigate to the target directory and run the application:

```sh
java -jar target/spring-boot-boilerplate.jar
```

## Using Swagger
Once the application is running, you can access the Swagger UI at:

```sh
http://localhost:8080/api-docs
```

This provides interactive documentation for your API endpoints.
