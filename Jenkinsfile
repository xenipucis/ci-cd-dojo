#!groovy

pipeline {
    agent any
    parameters {
        string(name: 'APPLICATION_NAME', defaultValue: 'ninja', description: 'The name of the application on the Docker and pipeline context.')
        string(name: 'APPLICATION_TAG_VERSION', defaultValue: 'v0.0.1-WIP', description: 'The application version to be tagged on Docker.')
    }
    environment {
        MVN_ARGS='-v /tmp/ninja/.m2:/root/.m2'
        DOCKER_HUB_ACCOUNT='javapi'
    }
    stages {
        stage('Maven Test') {
            agent {
                docker {
                    image 'maven:3.3.3'
                    args "${params.MAVEN_ARGS}"
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
                    args "${params.MAVEN_ARGS}"
                }
            }
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Docker Build') {
            agent any
            steps {
                sh "docker build -t ${DOCKER_HUB_ACCOUNT}/ninja:v4 ."
            }
        }

        stage('Docker TAG QA') {
            agent any
            steps {
                sh "docker tag ${DOCKER_HUB_ACCOUNT}/ninja:v4 ${DOCKER_HUB_ACCOUNT}/ninja-qa:v4"
            }
        }
        stage('Docker Push QA') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_HUB_ACCOUNT}/ninja-qa:v4"
                }
            }
        }
        stage('Docker Deploy QA') {
            agent any
            steps {
                sh 'docker container rm -f ninja-belt-qa || true'
                sh "docker run --network dd-network -d -p 8085:8081 --name ninja-belt-qa --hostname ninja-belt-qa ${DOCKER_HUB_ACCOUNT}/ninja-qa:v4"

            }
        }
//        stage('Integration/E2E Tests (On QA)') {
//            agent {
//                docker {
//                    image 'maven:3.3.3'
//                    args "${params.MAVEN_ARGS} --network dd-network"
//                }
//            }
//            steps {
//                sh 'mvn clean test -f ./ci/pom.xml'
//            }
//        }

        stage('Docker TAG PROD') {
            agent any
            steps {
                sh "docker tag ${DOCKER_HUB_ACCOUNT}/ninja:v4 ${DOCKER_HUB_ACCOUNT}/ninja-prod:v4"
            }
        }
        stage('Docker Push PROD') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_HUB_ACCOUNT}/ninja-prod:v4"
                }
            }
        }
        stage('Docker Deploy PROD') {
            agent any
            steps {
                sh 'docker container rm -f ninja-belt-prod || true'
                sh "docker run --network dd-network -d -p 8086:8081 --name ninja-belt-prod --hostname ninja-belt-prod ${DOCKER_HUB_ACCOUNT}/ninja-prod:v4"
            }
        }
    }
}