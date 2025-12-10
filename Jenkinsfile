pipeline {
  agent any

  tools {
    maven 'Maven 3.9'
    jdk 'JDK 17'
  }

  environment {
    SONAR_HOST_URL = 'http://sonarqube:9000'
    SONAR_TOKEN = credentials('sonar-token')
    DOCKER_IMAGE = 'abroud111/refactoringfinanceapp'
    DOCKER_TAG = "${env.BUILD_NUMBER}"
  }

  stages {
    stage('Checkout') {
      steps {
        echo '🔍 Checking out code from repository...'
        checkout scm
      }
    }

    stage('Set Environment') {
      steps {
        echo '⚙️ Setting up Java and Maven environment...'
        sh 'java -version'
        sh 'mvn -version'
      }
    }

    stage('Build') {
      steps {
        echo '🔨 Building Maven project...'
        sh 'mvn clean compile -DskipTests'
      }
    }

    stage('Unit Tests') {
      steps {
        echo '🧪 Running unit tests...'
        sh 'mvn test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('JaCoCo Coverage') {
      steps {
        echo '📊 Generating code coverage report...'
        sh 'mvn jacoco:report'
      }
      post {
        success {
          jacoco(
            execPattern: '**/target/jacoco.exec',
            classPattern: '**/target/classes',
            sourcePattern: '**/src/main/java'
          )
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        echo '🔍 Running SonarQube analysis...'
        withSonarQubeEnv('SonarQube') {
          sh """
            mvn sonar:sonar \
              -Dsonar.projectKey=refactored-finance \
              -Dsonar.host.url=${SONAR_HOST_URL} \
              -Dsonar.login=${SONAR_TOKEN}
          """
        }
      }
    }

    stage('Quality Gate') {
      steps {
        echo '🚦 Waiting for SonarQube Quality Gate...'
        timeout(time: 5, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }

    stage('Package') {
      steps {
        echo '📦 Packaging application...'
        sh 'mvn package -DskipTests'
        sh 'ls -l target || true'
      }
      post {
        success {
          archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
      }
    }

    stage('Docker Build') {
      steps {
        echo '🐳 Building Docker image...'
        sh 'docker --version'
        sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} -t ${DOCKER_IMAGE}:latest ."
        sh "docker images | grep '${DOCKER_IMAGE}\\s' || true"
      }
    }

    stage('Docker Login (isolated config)') {
      steps {
        echo '🔑 Logging into Docker Hub with isolated DOCKER_CONFIG...'
        script {
          // Use a per-build docker config directory
          sh 'mkdir -p .docker-config'
          withEnv(["DOCKER_CONFIG=${pwd()}/.docker-config"]) {
            // Clean any prior auth
            sh 'docker logout https://index.docker.io/v1/ || true'
            sh 'docker logout registry-1.docker.io || true'
            sh 'docker logout docker.io || true'
            // Login with Jenkins credentials (prefer a Docker Hub access token as password)
            withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
              sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
            }
            // Show the exact config being used
            sh 'echo "Using DOCKER_CONFIG=$DOCKER_CONFIG"'
            sh 'cat "$DOCKER_CONFIG/config.json" || true'
          }
        }
      }
    }

    stage('Docker Push') {
      steps {
        echo '🚀 Pushing Docker image to Docker Hub...'
        script {
          withEnv(["DOCKER_CONFIG=${pwd()}/.docker-config"]) {
            // Confirm tags exist before pushing
            sh "docker images | awk '{print \$1\":\"\$2}' | grep '${DOCKER_IMAGE}:${DOCKER_TAG}' || (echo 'Tag not found' && exit 1)"
            sh "docker images | awk '{print \$1\":\"\$2}' | grep '${DOCKER_IMAGE}:latest' || (echo 'Tag not found' && exit 1)"
            // Push using the authenticated session in DOCKER_CONFIG
            sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
            sh "docker push ${DOCKER_IMAGE}:latest"
          }
        }
      }
    }
  }

  post {
    always {
      echo '🧹 Pipeline completed'
      deleteDir()
    }
    success {
      echo '✅ Pipeline completed successfully!'
    }
    failure {
      echo '❌ Pipeline failed!'
    }
  }
}