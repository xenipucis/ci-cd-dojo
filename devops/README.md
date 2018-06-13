
Build docker images using provided Dockerfiles

```markdown
# Jenkins
docker build --no-cache --file ./jenkins/Dockerfile -t dockerdonegal/dd-jenkins:latest ./jenkins
docker push dockerdonegal/dd-jenkins:latest
# Mongo DB
docker build --no-cache --file ./mongo/Dockerfile -t dockerdonegal/dd-mongo:latest ./mongo
docker push dockerdonegal/dd-mongo:latest
# Postgres
docker build --no-cache --file ./postgres/Dockerfile -t dockerdonegal/dd-postgres:latest ./postgres
docker push dockerdonegal/dd-postgres:latest
# SonarQube
docker build --no-cache --file ./sonarqube/Dockerfile -t dockerdonegal/dd-sonarqube:latest ./sonarqube
docker push dockerdonegal/dd-sonarqube:latest
```

Connect/Init Swarm
```
docker swarm init

```

If there's any incorrect behaviour related to an image (with volumes)
```
docker system prune --volumes
```

Create Network
```
docker network create --driver overlay --attachable dd-network

```

Pull the images
```
docker-compose pull
```

Deploy stack
```
docker stack deploy --compose-file docker-compose.yml ddninja
```

Delete Stack
```
docker stack rm $(docker stack ls --format '{{.Name}}')
docker container prune -f
```

Check created services
```
docker service ls
```

* Where do I find the syntax for docker-compose.yml?