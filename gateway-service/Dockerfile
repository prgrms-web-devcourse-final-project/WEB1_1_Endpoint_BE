FROM eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

# 환경변수 추가
ARG MODULE_NAME=gateway-service
ARG VERSION=0.0.1

COPY ../ .


RUN ./gradlew ${MODULE_NAME}:build --parallel -x test
RUN mkdir -p ${MODULE_NAME}/build/extracted && (java -Djarmode=layertools -jar ${MODULE_NAME}/build/libs/${MODULE_NAME}-${VERSION}.jar extract --destination ${MODULE_NAME}/build/extracted)

FROM eclipse-temurin:17-jdk
VOLUME /tmp

RUN apt-get update && apt-get install -y curl

ARG MODULE_NAME=gateway-service
ARG EXTRACTED=/workspace/app/${MODULE_NAME}/build/extracted

COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]
