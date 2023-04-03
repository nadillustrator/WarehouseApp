This educational test REST application is used to automate the accounting of socks in the warehouse of the store. The storekeeper has the ability to:
- take into account the incomes and outcomes of socks;
- find out the total number of socks, as well as get the corresponding list of socks of a certain color and composition at a given time.
For more information about how methods work, see javadoc and API doc(Swagger).

The database was created using Postgres and Liquibase. To use it, you need to create a database on the local computer and fill in the appropriate fields in the application.properties
Liquibase will create all the required tables when the application starts.
The external interface of the application is presented as a REST API and is available at http://localhost:8080/swagger-ui/index.html#/ using Swagger.
The database schema is in the DB.png file.

Used technologies:
- JAVA 17
- Maven
- Spring Boot 3
- Liquibase
- Postgres
- Lombok
- Swagger