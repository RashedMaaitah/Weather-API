
# Weather API

This is a solution for the Weather API project on 
https://roadmap.sh/projects/weather-api-wrapper-service

### Requirements:
- Java 21
- Redis Server https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/install-redis-on-windows/

### How to use:
This is a Spring Boot Application with a maven wrapper
so you can easily run it with **./mvnw spring-boot:run** 

#### Endpoints (Assuming localhost:8080):
- **localhost:8080/{countryName}** this will retrieve todays weather status in simple terms in JSON

- **localhost:8080/{countryName}/all** this wil retrieve the 15 upcomming days including todays weather status in simple terms





