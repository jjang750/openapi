# openapi
----

## Open Api 개발
  1. Content type Json
  2. Rest Api
  3. Path Variable
  4. Spring boot 
  5. Spring Security
  6. MyBatis
  7. Thymeleaf
  8. JDK 11
  9. Postgresql
 10. Swagger
---------
## application.properties Environment
  PS_HOST=PostgreSQL URL;
  PS_USER=PostgreSQLUser ID;
  PS_PASSWORD=PostgreSQLPassword
  ```
  #DATABASE
  spring.datasource.url=jdbc:postgresql://${PS_HOST}:54321/${PS_USER}?currentSchema=public
  spring.datasource.username=${PS_USER}
  spring.datasource.password=${PS_PASSWORD}
  spring.datasource.driver-class-name=org.postgresql.Driver
  ```
