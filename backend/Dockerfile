# Making a jar file
#FROM maven:3.9.6-amazoncorretto-17 AS builder

#WORKDIR /usr/src/backend

#COPY ./Rimuok-lt .

#RUN mvn clean package

# Making a build
#FROM openjdk:17-jdk-slim

#WORKDIR /use/src/backend

#COPY --from=builder ./Rimuok-lt/target/Rimuok.lt-1.0.jar .

#EXPOSE 8081

#CMD ["java", "-jar", "Rimuok.lt-1.0.jar"]

#FROM openjdk:17

#WORKDIR /usr/src/backend

#COPY ./Rimuok-lt .

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./Rimuok-lt/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]