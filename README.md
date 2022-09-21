# Kitchen

## Description

Network programming Kitchen server.

## Create .jar file

```bash
$ mvn clean package
```

## Docker build

```bash
$ docker build . -tag="username"/kitchen-docker:latest
```

## Push image to docker.io

```bash
$ docker push "username"/kitchen-docker
```

## Docker compose to run the Application

```bash
$ docker-compose up --build --remove-orphans
```