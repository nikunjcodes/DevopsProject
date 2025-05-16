// Jenkinsfile
pipeline {
    agent any

    environment {
        EC2_HOST = "ec2-user@YOUR_EC2_PUBLIC_IP"
        EC2_KEY  = "/home/jenkins/.ssh/your-key.pem"
        COMPOSE_FILE = "docker-compose.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    def services = [
                        [name: "flightservice", path: "flightservice"],
                        [name: "userservice",  path: "userservice"],
                        [name: "tickerservice",path: "tickerservice"]
                    ]
                    services.each { svc ->
                        dir(svc.path) {
                            sh "docker build -t ${svc.name}:latest ."
                        }
                    }
                }
            }
        }

        stage('Transfer Images to EC2') {
            steps {
                script {
                    ["flightservice","userservice","tickerservice"].each { svc ->
                        sh """
                        docker save ${svc}:latest \
                          | bzip2 \
                          | ssh -i ${EC2_KEY} ${EC2_HOST} 'bunzip2 | docker load'
                        """
                    }
                }
            }
        }

        stage('Transfer Compose File') {
            steps {
                sh "scp -i ${EC2_KEY} ${COMPOSE_FILE} ${EC2_HOST}:/home/ec2-user/${COMPOSE_FILE}"
            }
        }

        stage('Remote Deploy') {
            steps {
                sh """
                ssh -i ${EC2_KEY} ${EC2_HOST} << 'EOF'
                  cd /home/ec2-user
                  docker compose down
                  docker compose up -d
                EOF
                """
            }
        }
    }

    post {
        success { echo 'Deployment succeeded.' }
        failure { echo 'Deployment failed.' }
    }
}
