pipeline {
  agent any

  stages {
    stage('Build Back') {
      steps {
        sh """
          if docker ps -a | grep -q rimuok-back; then
            docker stop rimuok-back || true
            docker rm rimuok-back || true
          fi
        """

        sh """
          docker build -t rimuok-back ./backend/Rimuok-lt
        """
      }
    }

    stage('Build Front') {
      steps {
        sh """
          if docker ps -a | grep -q rimuok-front; then
            docker stop rimuok-front || true
            docker rm rimuok-front || true
          fi
        """

        sh """
          docker build -t rimuok-front ./frontend/rimuok-lt
        """
      }
    }

    stage('Deploy Back') {
      steps {
        sh """
          docker run -d --name rimuok-back -p 8083:8081 --restart=always rimuok-back
        """
      }
    }

    stage('Deploy Front') {
      steps {
        sh """
          docker run -d --name rimuok-front -p 8073:3000 --restart=always rimuok-front
        """
      }
    }
  }
}