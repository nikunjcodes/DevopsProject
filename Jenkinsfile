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
                    services.each { svc ->
                        echo "🔨 Building image for ${svc}"
                        sh "docker build -t ${svc}:latest ${svc}"
                    }
                }
            }
        }

        stage('Start Services') {
            steps {
                echo "🚀 Starting all services using Docker Compose"
                sh 'docker-compose up -d'
            }
        }

        stage('Wait & Health Checks') {
            steps {
                script {
                    echo "⏳ Waiting for services to be up..."
                    sleep(time: 20, unit: 'SECONDS')

                    def healthEndpoints = [
                        [name: 'flightservice', port: 8080],
                        [name: 'userservice', port: 8081],
                        [name: 'tickerservice', port: 8082],
                    ]

                    healthEndpoints.each { svc ->
                        echo "🔍 Checking ${svc.name} on port ${svc.port}"
                        sh "curl --fail --silent http://localhost:${svc.port}/actuator/health || exit 1"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ All microservices are up and healthy."
        }
        failure {
            echo "❌ One or more microservices failed health checks."
            sh 'docker-compose logs'
        }
        always {
            echo "🧹 Cleaning up containers..."
            sh 'docker-compose down'
        }
    }
}
