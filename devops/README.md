
Build docker images using provided Dockerfiles

```markdown
# Jenkins
docker build --file ./jenkins/Dockerfile -t dockerdonegal/dd-jenkins:latest ./jenkins
docker push dockerdonegal/dd-jenkins:latest
# Mongo DB
docker build --file ./mongo/Dockerfile -t dockerdonegal/dd-mongo:latest ./mongo
docker push dockerdonegal/dd-mongo:latest
# Postgres
docker build --file ./postgres/Dockerfile -t dockerdonegal/dd-postgres:latest ./postgres
docker push dockerdonegal/dd-postgres:latest
# SonarQube
docker build --file ./sonarqube/Dockerfile -t dockerdonegal/dd-sonarqube:latest ./sonarqube
docker push dockerdonegal/dd-sonarqube:latest
```

Deploy stack
```markdown
docker stack deploy --compose-file docker-compose.yml ddninja
```

Delete Stack
```markdown
docker stack rm $(docker stack ls --format '{{.Name}}')
docker container prune -f
```
