#!groovy

pipeline {
    agent none
    stages {
        stage('Maven Test') {
            agent {
                docker {
                    image 'maven:3.3.3'
                    args '-v /tmp/ninja/.m2:/root/.m2'
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
                sh 'docker build -t dockerdonegal/ninja:v4 .'
            }
        }

        stage('Docker TAG QA') {
            agent any
            steps {
                sh 'docker tag dockerdonegal/ninja:v4 dockerdonegal/ninja-qa:v4'
            }
        }
        stage('Docker Push QA') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push dockerdonegal/ninja-qa:v4'
                }
            }
        }
        stage('Docker Deploy QA') {
            agent any
            steps {
                //TODO figure out how to rm optionally without failing
//                sh 'docker container rm -f ninja-belt-qa'
                sh 'docker run --network dd-network -d -p 8085:8081 --name ninja-belt-qa --hostname ninja-belt-qa dockerdonegal/ninja-qa:v4'

            }
        }
        stage('Integration/E2E Tests (On QA)') {
            agent {
                docker {
                    image 'maven:3.3.3'
                    args '-v /tmp/ninja/.m2:/root/.m2 --network dd-network'
                }
            }
            steps {
                sh 'mvn clean test -f ./ci/pom.xml'
            }
        }

        stage('Docker TAG PROD') {
            agent any
            steps {
                sh 'docker tag dockerdonegal/ninja:v4 dockerdonegal/ninja-prod:v4'
            }
        }
        stage('Docker Push PROD') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push dockerdonegal/ninja-prod:v4'
                }
            }
        }
        stage('Docker Deploy PROD') {
            agent any
            steps {
                //TODO figure out how to rm optionally without failing
//                sh 'docker container rm -f ninja-belt-prod'
                sh 'docker run --network dd-network -d -p 8085:8081 --name ninja-belt-prod --hostname ninja-belt-prod dockerdonegal/ninja-prod:v4'

            }
        }
    }
}