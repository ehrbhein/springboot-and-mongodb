PROJECT_NAME=springboot-and-mongodb

.PHONY: start-db
start-db:
	docker compose up

.PHONY: start-app
start-app:
	mvn spring-boot:run

# TODO: add containerized implementation
# .PHONY: start-app
# start-app:
# 	$(MAKE) build
# 	$(MAKE) buildImage
# 	docker run -p 8080:8080 --name ${PROJECT_NAME} ${PROJECT_NAME}:latest --env-file ./docker/.env

# TODO: add containerized implementation
# .PHONY: buildImage
# buildImage:
# 	cd ./docker && docker build -t ${PROJECT_NAME}:latest .

# TODO: add containerized implementation
# .PHONY: build
# build:
# 	rm -rf ./docker/${PROJECT_NAME}-0.0.1.jar
# 	mvn -DskipTests package
# 	cp ./target/${PROJECT_NAME}-0.0.1.jar ./docker