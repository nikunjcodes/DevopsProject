pipeline {
    agent any

    environment {
        COMPOSE_PROJECT_NAME = "microservices"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    def services = ['flightservice', 'userservice', 'ticketservice']
                    for (svc in services) {
                        echo "🔨 Building Docker image for ${svc}"
                        sh "docker build -t ${svc}:latest ${svc}"
                    }
                }
            }
        }

        stage('Start Services with Docker Compose') {
            steps {
                echo "🚀 Starting all services with Docker Compose"
                sh 'docker compose up -d'
            }
        }
    }

    post {
        success {
            echo "✅ All microservices started successfully."
        }
        failure {
            echo "❌ Something went wrong."
            sh 'docker compose logs || true'
        }
        always {
            echo "🧹 Cleaning up containers..."
            sh 'docker compose down || true'
        }
    }
}
