# LED Store

## Description

This is an online store application for ordering LED linear lights. The application is built using Spring Boot with Java 17, PostgreSQL, and Maven.

LED linear lights available in our store have the following properties:

- Power
- Light flux
- Color temperature
- Length
- Profile type

## Setup

### Prerequisites

- Java 17
- PostgreSQL
- Maven

### Installation

1. **Clone the repository**: Clone this repository to your local machine.

2. **Set up the database**: Install PostgreSQL and create a new database named `led-store`.

3. **Configure application.properties**: In the `src/main/resources` directory, update the `application.properties` file with your PostgreSQL username and password:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/led-store
    spring.datasource.username=your username
    spring.datasource.password=your password
    ```

4. **Run DDL and DML scripts**: Navigate to the `src/main/resources/db/migration` directory and run the provided DDL (Data Definition Language) and DML (Data Manipulation Language) scripts to set up and populate your database. You can use a PostgreSQL client like `psql`:

    ```bash
    psql -U username -d led-store -a -f path_to_ddl_file
    psql -U username -d led-store -a -f path_to_dml_file
    ```

5. **Build the project**: Navigate to the project directory and use Maven to build the project:

    ```bash
    mvn clean install
    ```

6. **Run the application**: After the build is successful, you can run the application:

    ```bash
    java -jar target/led-store-0.0.1-SNAPSHOT.jar
    ```
