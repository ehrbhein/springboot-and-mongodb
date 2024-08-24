# springboot-mongodb-demo

![java](https://img.shields.io/badge/java-21-green)
![maven](https://img.shields.io/badge/maven-3.3.3-yellow)

## Running the application

![docker-compose](https://img.shields.io/badge/docker%20compose-blue)
![docker](https://img.shields.io/badge/docker-blue)
![maven](https://img.shields.io/badge/make-red)

- `make start-db` - runs docker compose with mongo-express and mongo-db.
- `make start-app` - runs spring-boot application. ⚠ Running the app using this command does not run it using docker container (yet).

## Accessing Mongo Express

Mongo-express would take time to access mongo database. So if we jump to the browser and try to access it after running `docker compose up`, we would get an error. 💡 Just wait for the these lines to appear on the terminal 👇:


```
mongo-express-1  | No custom config.js found, loading config.default.js
mongo-express-1  | Welcome to mongo-express 1.0.2
mongo-express-1  | ------------------------
mongo-express-1  | 
```

Access the mongo-express via browser on `localhost:8081`.

#### Mongo express credentials

See values inside the double quote. These lines also appears on the terminal when mongo-express is ready.


```
mongo-express-1  | Server is open to allow connections from anyone (0.0.0.0)
mongo-express-1  | basicAuth credentials are "admin:pass", it is recommended you change this in your config.js!
```

#### Creating the app database

Access mongo-express through the browser and create new database: `simpleDB`.

⚠ This needs to be done before running `make start-app`.

## Accessing the GET endpoint

Run this curl command on your terminal to perform a `GET` request.
```
$ curl http://localhost:8080/api/v1/students
```