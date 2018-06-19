Add entry **ci-cd.dockerdonegal.ie** to your **/etc/hosts**
```
127.0.0.1 ci-cd.dockerdonegal.ie
```

Login your docker account
```
docker login
```

Build docker images using provided Dockerfiles

```
# TODO Replace entries of javapi with your docker account.
# Jenkins
docker build --no-cache --file ./jenkins/Dockerfile -t javapi/dd-jenkins:latest ./jenkins
docker push javapi/dd-jenkins:latest
# Mongo DB
docker build --no-cache --file ./mongo/Dockerfile -t javapi/dd-mongo:latest ./mongo
docker push javapi/dd-mongo:latest
# Postgres
docker build --no-cache --file ./postgres/Dockerfile -t javapi/dd-postgres:latest ./postgres
docker push javapi/dd-postgres:latest
# SonarQube
docker build --no-cache --file ./sonarqube/Dockerfile -t javapi/dd-sonarqube:latest ./sonarqube
docker push javapi/dd-sonarqube:latest
```

Connect/Init Swarm
```
docker swarm init
```

If there's any incorrect behaviour related to an image (volumes)
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

* Go to Jenkins and set dockerHub global credentials.
* Go to Jenkins and set github global credentials.
---------------------------------------------------------


* Where do I find the syntax for docker-compose.yml?
* What is an overlay network?
* On the docker-compose.yml, why networks.dd-network.external = true?
* On the docker-compose.yml why volumes being declared at bottom?

> NOTE: 
> [create-credentials-from-groovy](https://support.cloudbees.com/hc/en-us/articles/217708168-create-credentials-from-groovy)