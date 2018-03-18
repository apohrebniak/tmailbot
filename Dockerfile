FROM openjdk:9.0-jre-slim
MAINTAINER Andrii Pohrebniak<andrii.pohrebniak@gmail.com>

EXPOSE 25

COPY build/libs/*.jar /app.jar

ENTRYPOINT java -jar ./app.jar