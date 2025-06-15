pipeline {
  agent any

  environment {
    IMAGE_NAME_F = 'rimuok-front_img'
    CONTAINER_NAME_F = 'rimuok-front'
    APP_PORT_F = '8073'
    CONTAINER_PORT_F = '3000'
    IMAGE_NAME_B = 'rimuok-back_img'
    CONTAINER_NAME_B = 'rimuok-back'
    APP_PORT_B = '8083'
    CONTAINER_PORT_B = '8081'
  }

  stages {
    stage('Build Back') {
      steps {
        sh """
          if docker images | grep -q ${IMAGE_NAME_B}; then
            docker rmi -f ${IMAGE_NAME_B} || true
          fi
        """

        sh """
          docker build -t ${IMAGE_NAME_B} ./backend/Rimuok-lt
        """
      }
    }

    stage('Build Front') {
      steps {
        sh """
          if docker images | grep -q ${IMAGE_NAME_F}; then
            docker rmi -f ${IMAGE_NAME_F} || true
          fi
        """

        sh """
          docker build -t ${IMAGE_NAME_F} ./frontend/rimuok-lt
        """
      }
    }

    stage('Deploy Back') {
      steps {
        sh """
          if docker ps -a | grep -q ${CONTAINER_NAME_B}; then
            docker stop ${CONTAINER_NAME_B} || true
            docker rm ${CONTAINER_NAME_B} || true
          fi
        """

        sh """
          docker run -d --name ${CONTAINER_NAME_B} -p ${APP_PORT_B}:${CONTAINER_PORT_B} --restart=always ${IMAGE_NAME_B}
        """
      }
    }

    stage('Deploy Front') {
      steps {
        sh """
          if docker ps -a | grep -q ${CONTAINER_NAME_F}; then
            docker stop ${CONTAINER_NAME_F} || true
            docker rm ${CONTAINER_NAME_F} || true
          fi
        """

        sh """
          docker run -d --name ${CONTAINER_NAME_F} -p ${APP_PORT_F}:${CONTAINER_PORT_F} --restart=always ${IMAGE_NAME_F}
        """
      }
    }
  }
}