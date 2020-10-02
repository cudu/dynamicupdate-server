### Build

Build an executable jar file(with tests & integration tests):

```
mvn verify
```


### Run

Environment variables are required to run the application:
* DB_USERNAME[value=sa]
* DB_PASSWORD[value=password]

Run an executable jar file:
```
java -jar target/dynamic-update-server.jar -Dspring.profiles.active=h2
```

Spring boot run:

```
mvn spring-boot:run
```