COMPOSE = docker compose -f infrastructure/docker-compose/common.yml

.PHONY: all-start all-down es-up es-down kafka-up kafka-down redis-up redis-down logstash-up logstash-down

all-start:
	$(COMPOSE) \
		-f infrastructure/docker-compose/kafka/zookeeper.yml \
		-f infrastructure/docker-compose/kafka/kafka_cluster.yml \
		-f infrastructure/docker-compose/connect/connect.yml \
		-f infrastructure/docker-compose/es/docker-compose.yml \
		-f infrastructure/docker-compose/redis/redis.yml \
		-f infrastructure/docker-compose/logstash/logstash.yml up -d

all-down:
	$(COMPOSE) \
		-f infrastructure/docker-compose/kafka/zookeeper.yml \
		-f infrastructure/docker-compose/kafka/kafka_cluster.yml \
		-f infrastructure/docker-compose/es/docker-compose.yml \
		-f infrastructure/docker-compose/redis/redis.yml \
		-f infrastructure/docker-compose/logstash/logstash.yml down -v

es-up:
	$(COMPOSE) -f infrastructure/docker-compose/es/docker-compose.yml up -d

es-down:
	$(COMPOSE) -f infrastructure/docker-compose/es/docker-compose.yml down

kafka-up:
	$(COMPOSE) -f infrastructure/docker-compose/kafka/zookeeper.yml \
		-f infrastructure/docker-compose/kafka/kafka_cluster.yml up -d

kafka-down:
	$(COMPOSE) -f infrastructure/docker-compose/kafka/zookeeper.yml \
		-f infrastructure/docker-compose/kafka/kafka_cluster.yml down -v

redis-up:
	$(COMPOSE) -f infrastructure/docker-compose/redis/redis.yml up -d

redis-down:
	$(COMPOSE) -f infrastructure/docker-compose/redis/redis.yml down

logstash-up:
	$(COMPOSE) -f infrastructure/docker-compose/logstash/logstash.yml up -d

logstash-down:
	$(COMPOSE) -f infrastructure/docker-compose/logstash/logstash.yml down