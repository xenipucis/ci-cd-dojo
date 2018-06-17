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
        stage("SonarQube analysis") {
            agent {
                docker {
                    image 'maven:3.3.3'
                }
            }
            steps {
                withSonarQubeEnv('sonarqubeServer') {
                    sh 'mvn clean package sonar:sonar'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
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
                sh 'echo ADDSTEP'

            }
        }
        stage('Integration/E2E Tests') {
            agent any
            steps {
                sh 'echo ADDSTEP'

            }
        }
        stage('Deploy PROD') {
            agent any
            steps {
                sh 'echo ADDSTEP'

            }
        }
    }
}