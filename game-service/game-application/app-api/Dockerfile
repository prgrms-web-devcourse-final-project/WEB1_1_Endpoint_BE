FROM eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

ARG MODULE_PATH=game-service/game-application/app-api
ARG MODULE_NAME=app-api
ARG VERSION=0.0.1

COPY ../.. .

RUN ./gradlew game-service:game-application:${MODULE_NAME}:clean
RUN ./gradlew game-service:game-application:${MODULE_NAME}:build --parallel -x test
RUN mkdir -p ${MODULE_PATH}/build/extracted && (java -Djarmode=layertools -jar ${MODULE_PATH}/build/libs/${MODULE_NAME}-${VERSION}.jar extract --destination ${MODULE_PATH}/build/extracted)

FROM eclipse-temurin:17-jdk
VOLUME /tmp

RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
ENV TZ=Asia/Seoul

RUN apt-get update && apt-get install -y curl

ARG MODULE_PATH=game-service/game-application/app-api
ARG EXTRACTED=/workspace/app/${MODULE_PATH}/build/extracted

COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]
