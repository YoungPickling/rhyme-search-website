FROM maven:3.9-amazoncorretto-17-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

ENV CONFIG_LINK=/app/src/main/resources/application.yaml

RUN sed -i 's/localhost/192.168.10.250/' "$CONFIG_LINK" && \
    sed -i 's/maxim23/postgres/' "$CONFIG_LINK" && \
    mvn clean package -DskipTests

RUN rm -rf /root/.m2/repository

FROM eclipse-temurin:17-jre-noble

WORKDIR /app
COPY --from=build /app/target/*.jar rimuok_lt_backend.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "rimuok_lt_backend.jar"]