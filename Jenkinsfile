#!groovy

pipeline {
    agent none
    stages {
        stage('Maven Test') {
            agent {
                docker {
                    image 'maven:3.3.3'
                }
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('Maven Package') {
            agent {
                docker {
                    image 'maven:3.3.3'
                }
            }
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker Build') {
            agent any
            steps {
                sh 'docker build -t dockerdonegal/ninja:v4 .'
            }
        }
//        stage('Docker Push') {
//            agent any
//            steps {
//                withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
//                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
//                    sh 'docker push dockerdonegal/ninja:v4'
//                }
//            }
//        }
        stage('Deploy QA') {
            agent any
            steps {
                //TODO figure out how to rm optionally without failing
//                sh 'docker container rm -f ninja-belt-qa'
                sh 'docker run -d -p 85:8080 --name ninja-belt-qa dockerdonegal/ninja:v4'

            }
        }
//        stage('Integration/E2E Tests') {
//            agent {
//                docker {
//                    image 'maven:3.3.3'
//                }
//            }
//            steps {
//                sh 'mvn clean test -f ./ci/pom.xml'
//            }
//        }
//        stage('Deploy PROD') {
//            agent any
//            steps {
//                sh 'docker container rm -f ninja-belt-prod'
//                sh 'docker run -d -p 82:8080 --name ninja-belt-prod dockerdonegal/ninja:v4'
//
//            }
//        }
    }
}