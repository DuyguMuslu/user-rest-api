USER-REST-APPLICATION
----------------------------------------
This is a simple service for data creating, displaying and updating user via Rest services

Build
----- 
- Clone the project to your local environment (H2 Embedded DB is used)
    `mvnw clean install`
- In the project directory run 
    `./mvnw spring-boot:run`
 
Usage
---------------
- Base Path for the application is `http://localhost/api/v1`
- REST APIs can be found on following URL 
  http://localhost:8080/api/v1/upload
- Sample requests for user rest services are listed on a postman collection 
    https://www.getpostman.com/collections/2c88ee3f52f7bae04133
- Sample requests for pet rest services are listed on a postman 
    https://www.getpostman.com/collections/eb7d5001910121070a70 
        __Download__ : https://www.postman.com 
- DB Console can be accessed via http://localhost:8080/api/v1/h2-console/login.jsp
    .No password required, JDBC Url is `jdbc:h2:file:~/userdata-schema;AUTO_SERVER=TRUE` 
    
