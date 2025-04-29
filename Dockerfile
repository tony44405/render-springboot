FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/application.properties ./src/main/resources/application.properties

ENTRYPOINT ["java", "-jar", "app.jar"]