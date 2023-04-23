FROM openjdk:11-jre
MAINTAINER sang
COPY system/target/system-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

# 要想在 FROM 之后使用，必须再次指定
ARG ACTIVE
# ENTRYPOINT 只认 ENV 环境变量
ENV ACTIVE=${ACTIVE}

ENTRYPOINT "/bin/sh -c set -e && java -jar app.jar --spring.profiles.active=${ACTIVE}"
