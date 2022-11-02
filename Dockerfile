FROM openjdk:11
COPY target/dining-hall-docker.jar dining-hall-docker.jar
EXPOSE ${port}
ENTRYPOINT exec java -jar dining-hall-docker.jar