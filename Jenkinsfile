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
                sh 'docker build -t javapi/ci-cd-dojo:v0.0.1 .'
            }
        }
        stage('Docker Push') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push javapi/ci-cd-dojo:v0.0.1'
                }
            }
        }
        stage('Deploy QA') {
            agent any
            steps {
                sh 'docker run -d -p 85:8080 dockerdonegal/ninja:v4'

            }
        }
        stage('Integration/E2E Tests') {
            agent {
                docker {
                    image 'maven:3.3.3'
                }
            }
            steps {
                sh 'mvn clean test -f ./ci/pom.xml'
            }
        }
        stage('Deploy PROD') {
            agent any
            steps {
                sh 'docker run -d -p 82:8080 dockerdonegal/ninja:v4'

            }
        }
    }
}