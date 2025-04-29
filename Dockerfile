FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY target/BrotessApp-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties ./src/main/resources/application.properties

ENTRYPOINT ["java", "-jar", "app.jar"]