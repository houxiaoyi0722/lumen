FROM openjdk:11-jre
COPY system/target/system-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080


ARG profile=aaa
ENV profile=${profile}
CMD ["/bin/sh","-c","set -e && java -jar app.jar --spring.profiles.active=${profile}"]
