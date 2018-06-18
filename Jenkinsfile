#!groovy

pipeline {
    agent any
    parameters {
        string(name: 'DOCKER_HUB_ACCOUNT', defaultValue: 'dockerdonegal', description: 'The Docker Hub where youre pulling from and pushing to.')
        string(name: 'APPLICATION_NAME', defaultValue: 'ninja', description: 'The name of the application on the Docker and pipeline context.')
        string(name: 'APPLICATION_TAG_VERSION', defaultValue: 'v0.0.1-WIP', description: 'The application version to be tagged on Docker.')
        string(name: 'MAVEN_ARGS', defaultValue: '-v /tmp/ninja/.m2:/root/.m2', description: 'The otions to be passed as args to the maven image (docker).')
    }
    stages {
        stage('Maven Test') {
            agent {
                docker {
                    image 'maven:3.3.3'
                    args '${params.MAVEN_ARGS}'
                }
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('Maven Install') {
            agent {
                docker {
                    image 'maven:3.3.3'
                    args '-v /tmp/ninja/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Docker Build') {
            agent any
            steps {
                sh 'docker build -t javapi/ninja:v4 .'
            }
        }

        stage('Docker TAG QA') {
            agent any
            steps {
                sh 'docker tag javapi/ninja:v4 javapi/ninja-qa:v4'
            }
        }
        stage('Docker Push QA') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push javapi/ninja-qa:v4'
                }
            }
        }
        stage('Docker Deploy QA') {
            agent any
            steps {
                sh 'docker container rm -f ninja-belt-qa || true'
                sh 'docker run --network dd-network -d -p 8085:8081 --name ninja-belt-qa --hostname ninja-belt-qa javapi/ninja-qa:v4'

            }
        }
//        stage('Integration/E2E Tests (On QA)') {
//            agent {
//                docker {
//                    image 'maven:3.3.3'
//                    args '-v /tmp/ninja/.m2:/root/.m2 --network dd-network'
//                }
//            }
//            steps {
//                sh 'mvn clean test -f ./ci/pom.xml'
//            }
//        }

        stage('Docker TAG PROD') {
            agent any
            steps {
                sh 'docker tag javapi/ninja:v4 javapi/ninja-prod:v4'
            }
        }
        stage('Docker Push PROD') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push javapi/ninja-prod:v4'
                }
            }
        }
        stage('Docker Deploy PROD') {
            agent any
            steps {
                sh 'docker container rm -f ninja-belt-prod || true'
                sh 'docker run --network dd-network -d -p 8086:8081 --name ninja-belt-prod --hostname ninja-belt-prod javapi/ninja-prod:v4'

            }
        }
    }
}