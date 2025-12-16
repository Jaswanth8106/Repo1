pipeline {
    agent none

    tools {
        maven 'mymaven'
    }

    parameters {
        string(name: 'Env', defaultValue: 'Test', description: 'Environment name')
        booleanParam(name: 'executeTests', defaultValue: true, description: 'Run unit tests')
        choice(name: 'APPVERSION', choices: ['1.1', '1.2', '1.3'], description: 'Select application version')
    }

    stages {

        stage('Compile') {
            agent any
            steps {
                echo "Compiling the code in ${params.Env} environment"
                sh 'mvn clean compile'
            }
        }

        stage('Code Review') {
            agent any
            steps {
                echo 'Running static code analysis'
                sh 'mvn pmd:pmd'
            }
        }

        stage('Unit Test') {
            agent any
            when {
                expression { params.executeTests == true }
            }
            steps {
                echo 'Running unit tests'
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Coverage Analysis') {
            agent any
            steps {
                echo "Performing code coverage analysis for version ${params.APPVERSION}"
                sh 'mvn verify'
            }
        }

        stage('Package') {
            agent any
            steps {
                echo 'Packaging the application'
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
