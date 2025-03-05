
pipeline {
    agent any

    environment {
            DOCKER_IMAGE = 'thaonhi6991/tripcost'
            DOCKER_TAG = 'latest'
        }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/nhidinh91/TripCostCalculator.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Code Coverage') {
                    steps {
                        bat 'mvn jacoco:report'
                    }
                }
                stage('Publish Test Results') {
                    steps {
                        junit '**/target/surefire-reports/*.xml'
                    }
                }
                stage('Publish Coverage Report') {
                    steps {
                        jacoco()
                    }
                }
                stage('Build Docker Image') {
                            steps {
                                script {
                                   if (isUnix()) {
                                        sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                                    } else {
                                        bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                                    }
                                }
                            }
                        }

                        stage('Push Docker Image to Docker Hub') {
                            steps {
                                script {
                                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                                        docker.image(DOCKER_IMAGE).push(DOCKER_TAG)
                                    }
                                }
                            }
                        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            jacoco execPattern: '**/target/jacoco.exec'
        }
    }
}

