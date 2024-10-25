FROM 10.144.233.86:8082/sang/arthas:3.7.2
MAINTAINER sang
COPY system/target/system-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

# 要想在 FROM 之后使用，必须再次指定
ARG ACTIVE
# ENTRYPOINT 只认 ENV 环境变量
ENV ACTIVE=${ACTIVE}

ENTRYPOINT java -jar app.jar -Xmx2g -Xms1g -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/data/jvm/heap.dump --spring.profiles.active=${ACTIVE}
