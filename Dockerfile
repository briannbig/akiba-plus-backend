FROM openjdk:17-slim

RUN mkdir -p /app

COPY ./build/libs/akiba.jar /app/

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "akiba.jar"]