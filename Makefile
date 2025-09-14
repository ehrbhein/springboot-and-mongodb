.PHONY: start-app
start-app:
	docker compose up -d

.PHONY: stop-app
stop-app:
	docker compose down

.PHONY: build
build:
	mvn compile package

.PHONY: clean
clean:
	mvn clean
