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
      }
      post {
        success {
          archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
      }
    }

    // Build and tag only on main
    stage('Docker Build') {
      when { branch 'main' }
      steps {
        echo '🐳 Building Docker image (main)...'
        sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} -t ${DOCKER_IMAGE}:latest ."
      }
    }

    // Push only on main, using Jenkins credentials
    stage('Docker Push') {
      when { branch 'main' }
      steps {
        echo '🚀 Pushing Docker image to registry (main)...'
        script {
          docker.withRegistry('', 'docker-credentials') {
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