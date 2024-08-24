# stage 1: build the app using  maven
FROM maven:3.8.8-amazoncorretto-21 AS build
WORKDIR /usr/build-app
COPY . .
ENTRYPOINT ["mvn", "package"]


# stage 2: run the java application
FROM openjdk:22-ea-21-slim
COPY --from=build /usr/build-app/springboot-and-mongodb-0.0.1.jar .
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Xms254m -Xmx589m -jar /springboot-and-mongodb-0.0.1.jar"]