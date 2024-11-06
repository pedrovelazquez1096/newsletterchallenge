FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR .
COPY . .
RUN ls -alh
RUN mvn clean package

FROM amazoncorretto:21-alpine3.17-jdk
COPY --from=build /target/newslettlerchallenge-0.0.1-SNAPSHOT.jar /usr/src/build.jar
CMD["java","-jar /usr/src/build.jar"]