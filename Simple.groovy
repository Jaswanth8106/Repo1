pipeline {
    agent any

    tools {
        jdk 'java17'
        maven 'mymaven'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Jaswanth8106/Repo1.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
