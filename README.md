# springboot-mongodb-demo

![java](https://img.shields.io/badge/java-21-green)
![maven](https://img.shields.io/badge/maven-3.3.3-yellow)

## Running the application

![docker-compose](https://img.shields.io/badge/docker%20compose-blue)
![docker](https://img.shields.io/badge/docker-blue)
![maven](https://img.shields.io/badge/make-red)

- `make start-db` - runs docker compose with mongo-express and mongo-db.
- `make start-app` - runs spring-boot application. ⚠ Running the app using this command does not run it using docker container (yet).

## Accessing the server endpoints

Use the http file under the `/http` directory together with the `env.json` to make requests to the application.

For more context on how to use http files, refer to either of these guides:
- https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html
- https://learn.microsoft.com/en-us/aspnet/core/test/http-files?view=aspnetcore-9.0

## Open API documentation

Refer to this [file](./src/main/resources/openapi-spec.yaml) for full information of the different endpoints that will allow you to perform CRUD operations.

> If your IDE does not have support to render the openapi-spec, you can use the [free online swagger editor](https://editor.swagger.io/).

## Accessing mongodb (local)

Use this connection string: `mongodb://localhost:27017` on your [mongodb client](https://www.mongodb.com/try/download/compass) to connect to the database running on docker container.