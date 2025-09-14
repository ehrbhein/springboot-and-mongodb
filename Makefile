.PHONY: start-app
start-app:
	docker compose up -d

.PHONY: stop-app
stop-app:
	docker compose down