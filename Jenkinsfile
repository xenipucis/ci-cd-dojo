#!groovy

pipeline {
    agent any
    parameters {
        string(name: 'MAVEN_ARGS', defaultValue: '-v /tmp/ninja/.m2:/root/.m2', description: 'The options to be passed as args to the maven image (docker).')
    }
    environment {
        MAVEN_ARGS='-v /tmp/ninja/.m2:/root/.m2'
        DOCKER_HUB_ACCOUNT='dockerdonegal'
        APPLICATION_NAME='ninja'
        APPLICATION_TAG_VERSION='v0.0.1-rc3'
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
                sh "docker build -t ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}:${env.APPLICATION_TAG_VERSION} ."
            }
        }

        stage('Docker TAG QA') {
            agent any
            steps {
                sh "docker tag ${env.DOCKER_HUB_ACCOUNT}/ninja:${env.APPLICATION_TAG_VERSION} ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}-qa:${env.APPLICATION_TAG_VERSION}"
            }
        }
        stage('Docker Push QA') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}-qa:${env.APPLICATION_TAG_VERSION}"
                }
            }
        }
        stage('Docker Deploy QA') {
            agent any
            steps {
                sh 'docker container stop ninja-belt-qa || true'
                sh 'docker network disconnect dd-network ninja-belt-qa || true'
                sh 'docker container rm -f ninja-belt-qa || true'
                sh "docker run -d -p 8085:8081 --name ninja-belt-qa -h ninja-belt-qa --network dd-network --ip 10.0.0.11 ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}-qa:${env.APPLICATION_TAG_VERSION}"

            }
        }
        stage('Integration/E2E Tests (On QA)') {
            agent {
                docker {
                    image 'maven:3.3.3'
                    args "${params.MAVEN_ARGS} --network dd-network"
                }
            }
            steps {
                sh 'mvn clean test -f ./ci/pom.xml'
            }
        }
        stage('Docker TAG PROD') {
            agent any
            steps {
                sh "docker tag ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}:v4 ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}-prod:${env.APPLICATION_TAG_VERSION}"
            }
        }
        stage('Docker Push PROD') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}-prod:${env.APPLICATION_TAG_VERSION}"
                }
            }
        }
        stage('Docker Deploy PROD') {
            agent any
            steps {
                sh 'docker container stop ninja-belt-prod || true'
                sh 'docker network disconnect dd-network ninja-belt-prod || true'
                sh 'docker container rm -f ninja-belt-prod || true'
                sh "docker run -d -p 8086:8081 --name ninja-belt-prod -h ninja-belt-prod --network dd-network --ip 10.0.0.13 ${env.DOCKER_HUB_ACCOUNT}/${env.APPLICATION_NAME}-prod:${env.APPLICATION_TAG_VERSION}"
            }
        }
    }
}
