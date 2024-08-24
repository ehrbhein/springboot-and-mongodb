# springboot-mongodb-demo

![java](https://img.shields.io/badge/java-21-green)
![maven](https://img.shields.io/badge/maven-3.3.3-yellow)

## Running the application

![docker-compose](https://img.shields.io/badge/docker%20compose-blue)
![docker](https://img.shields.io/badge/docker-blue)
![maven](https://img.shields.io/badge/make-red)

- `make start` - runs docker compose and starts the application.

## Accessing Mongo Express

Mongo-express would take time to access mongo database. So if we jump to the browser and try to access it after running `docker compose up`, we would get an error. 💡 Just wait for the these lines to appear on the terminal 👇:


```
mongo-express-1  | No custom config.js found, loading config.default.js
mongo-express-1  | Welcome to mongo-express 1.0.2
mongo-express-1  | ------------------------
mongo-express-1  | 
```

#### Mongo express credentials

See values inside the double quote. These lines also appears on the terminal when mongo-express is ready.


```
mongo-express-1  | Server is open to allow connections from anyone (0.0.0.0)
mongo-express-1  | basicAuth credentials are "admin:pass", it is recommended you change this in your config.js!
```