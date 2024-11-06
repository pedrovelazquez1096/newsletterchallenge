FROM maven:3.9.9-amazoncorretto-21 as build

WORKDIR .

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:21-ea-4-jdk-slim
WORKDIR .

COPY --from=build /target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]