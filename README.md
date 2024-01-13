# Akiba

---

1. Getting Started
### Spinning a docker postgres database instance

``` shell
docker run --name almondpostgres -p 5420:5432  -e POSTGRES_PASSWORD=almond -e POSTGRES_DB=almond -e POSTGRES_USER=almond -d postgres:alpine
```

### Running the application:
```shell
./gradlew bootRun
```

OpenAPI documentation:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)