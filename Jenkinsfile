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
                    def services = ['flightservice', 'userservice', 'tickerservice']
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

        stage('Wait and Run Health Checks') {
            steps {
                script {
                    echo "⏳ Waiting for services to become healthy..."
                    sleep(time: 20, unit: 'SECONDS')

                    def endpoints = [
                        [name: 'flightservice', port: 8080],
                        [name: 'userservice',  port: 8081],
                        [name: 'tickerservice', port: 8082],
                    ]

                    for (svc in endpoints) {
                        echo "🔍 Checking ${svc.name} at port ${svc.port}"
                        sh "curl --fail --silent http://localhost:${svc.port}/actuator/health || (echo '${svc.name} failed health check' && exit 1)"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ All microservices started and passed health checks."
        }
        failure {
            echo "❌ One or more services failed."
            sh 'docker compose logs || true'
        }
        always {
            echo "🧹 Cleaning up containers..."
            sh 'docker compose down || true'
        }
    }
}
