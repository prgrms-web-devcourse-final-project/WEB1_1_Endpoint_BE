all-start:
	docker compose -f infrastructure/common.yml -f infrastructure/kafka/zookeeper.yml -f infrastructure/kafka/kafka_cluster.yml -f infrastructure/connect/connect.yml up -d
	docker compose -f infrastructure/common.yml -f infrastructure/es/docker-compose.yml up -d
	docker compose -f infrastructure/common.yml -f infrastructure/redis/redis.yml up -d
	docker compose -f infrastructure/common.yml -f infrastructure/logstash/logstash.yml up -d
all-down:
	docker compose -f infrastructure/common.yml -f infrastructure/kafka/zookeeper.yml -f infrastructure/kafka/kafka_cluster.yml down
	docker compose -f infrastructure/common.yml -f infrastructure/es/docker-compose.yml down
	docker compose -f infrastructure/common.yml -f infrastructure/redis/redis.yml down
	docker compose -f infrastructure/common.yml -f infrastructure/logstash/logstash.yml down


es-up:
	docker compose -f infrastructure/common.yml -f infrastructure/es/docker-compose.yml up -d
es-down:
	docker compose -f infrastructure/common.yml -f infrastructure/es/docker-compose.yml down


kafka-up:
	docker compose -f infrastructure/common.yml -f infrastructure/kafka/zookeeper.yml -f infrastructure/kafka/kafka_cluster.yml up -d
kafka-down:
	docker compose -f infrastructure/common.yml -f infrastructure/kafka/zookeeper.yml -f infrastructure/kafka/kafka_cluster.yml down


redis-up:
	docker compose -f infrastructure/common.yml -f infrastructure/redis/redis.yml up -d
redis-down:
	docker compose -f infrastructure/common.yml -f infrastructure/redis/redis.yml down


logstash-up:
	docker compose -f infrastructure/common.yml -f infrastructure/logstash/logstash.yml up -d
logstash-down:
	docker compose -f infrastructure/common.yml -f infrastructure/logstash/logstash.yml down