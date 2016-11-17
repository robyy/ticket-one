Development Env:
Mac OS: 10.11.6
JDK 8
Maven > 3


Tech stack: React.js, Spring Boot, Spring Data JPA, Hibernate, Spring MVC
DB: Spring boot embedded h2
Web server: Spring boot embedded Tomcat


go to ticket-one folder where pom.xml is, run:
mvn clean package
Then 
mvn spring-boot:run 

Make sure 8080 port not used by other applications
H2 DB web console:
http://localhost:8080/h2-console/


Driver class:  org.h2.Driver
JDBC URL:  jdbc:h2:mem:AZ
Username: sa
Password: “leave it empty”

the SQL for default data inserted into DB during server startup located in data.sql(/src/main/resources/)


Login page:
http://localhost:8080/index.html


Rest API test example:
  http://localhost:8080/api/v1/orders/purchase?qty=200&eventid=3&username=robyy@qq.com1
  http://localhost:8080/api/v1/orders/purchase?qty=200&eventid=1&username=robyy@qq.com
  http://localhost:8080/api/v1/orders/cancellation?qty=200&eventid=1&username=robyy@qq.com
  http://localhost:8080/api/v1/orders/cancellation?qty=1&eventid=1&username=robyy@qq.com


  http://localhost:8080/api/v1/orders/exchange?qty=1&eventidf=3&eventidt=1&username=robyy@qq.com
  http://localhost:8080/api/v1/orders/robyy@qq.com
  http://localhost:8080/api/v1/orders/1/7
  
TODO plan:
Redesign REST API follow HATEOAS principle( with Spring HATEOAS).
Introduce Flux/Redux.
