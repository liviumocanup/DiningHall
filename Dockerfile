FROM openjdk:11
EXPOSE 8080
ADD target/dining-hall-docker.jar dining-hall-docker.jar
ENTRYPOINT ["java", "-jar", "/dining-hall-docker.jar"]