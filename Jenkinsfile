#!groovy

pipeline {
    agent any
    environment {
        DOCKER_HUB_ACCOUNT='javapi'
        APPLICATION_NAME='ninja'
        APPLICATION_TAG_VERSION='v0.0.1-WIP'
        MAVEN_ARGS='-v ./.m2:/root/.m2'

    }
    stages {
        stage('Maven Test') {
            agent {
                docker {
                    image 'maven:3.3.3'
                    args "$MAVEN_ARGS"
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
                    args "${MAVEN_ARGS}"
                }
            }
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Docker Build') {
            agent any
            steps {
                sh 'docker build -t ${DOCKER_HUB_ACCOUNT}/${APPLICATION_NAME}:v4 .'
            }
        }

        stage('Docker TAG QA') {
            agent any
            steps {
                sh 'docker tag javapi/ninja:v4 ${DOCKER_HUB_ACCOUNT}/${APPLICATION_NAME}-qa:v4'
            }
        }
        stage('Docker Push QA') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push ${DOCKER_HUB_ACCOUNT}/${APPLICATION_NAME}-qa:v4'
                }
            }
        }
        stage('Docker Deploy QA') {
            agent any
            steps {
                sh 'docker container rm -f ninja-belt-qa || true'
                sh 'docker run --network dd-network -d -p 8085:8081 --name ninja-belt-qa --hostname ninja-belt-qa ${DOCKER_HUB_ACCOUNT}/${APPLICATION_NAME}-qa:v4'

            }
        }
//        stage('Integration/E2E Tests (On QA)') {
//            agent {
//                docker {
//                    image 'maven:3.3.3'
//                    args '${MAVEN_ARGS} --network dd-network'
//                }
//            }
//            steps {
//                sh 'mvn clean test -f ./ci/pom.xml'
//            }
//        }

        stage('Docker TAG PROD') {
            agent any
            steps {
                sh 'docker tag ${DOCKER_HUB_ACCOUNT}/${APPLICATION_NAME}:v4 $DOCKER_HUB_ACCOUNT/${APPLICATION_NAME}-prod:v4'
            }
        }
        stage('Docker Push PROD') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push ${DOCKER_HUB_ACCOUNT}/${APPLICATION_NAME}-prod:v4'
                }
            }
        }
        stage('Docker Deploy PROD') {
            agent any
            steps {
                sh 'docker container rm -f ninja-belt-prod || true'
                sh 'docker run --network dd-network -d -p 8086:8081 --name ninja-belt-prod --hostname ninja-belt-prod ${DOCKER_HUB_ACCOUNT}/${APPLICATION_NAME}-prod:v4'

            }
        }
    }
}