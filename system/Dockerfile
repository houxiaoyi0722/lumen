FROM openjdk:8-jre
COPY target/lumen-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
EXPOSE 20880
CMD ["/bin/sh","-c","set -e && java -jar app.jar --spring.profiles.active=test"]
