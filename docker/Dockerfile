FROM openjdk:21

EXPOSE 8080

COPY /springboot-and-mongodb-0.0.1.jar springboot-and-mongodb-0.0.1.jar

ENTRYPOINT ["sh", "-c", "java -Xms1664m -Xmx3700m -jar /springboot-and-mongodb-0.0.1.jar"]