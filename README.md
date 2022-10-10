# Dining Hall

## Description

Network programming Dining Hall server.

## Create .jar file

```bash
$ mvn clean package
```

## Docker build

```bash
$ docker build . -tag="username"/dining-hall-docker:latest
```

## Push image to docker.io

```bash
$ docker push "username"/dining-hall-docker
```

## Docker compose to run the Application

```bash
$ docker-compose up --build --remove-orphans
```