pipeline {

  agent any

//  agent {
//    docker {
//      image 'maven:3.8.7-openjdk-18-slim'
//      args '-v /root/.m2:/root/.m2'
//    }
//  }

  environment {
    PROFILE = "test"
    DOCKER_REGISTY = "10.144.233.86:8082"
    pom = readMavenPom file: 'pom.xml'
    img_name = "${pom.artifactId}-${PROFILE}"
    img_version = "${pom.version}"
  }

  stages {

    stage('prepare') {
      steps {
        sh 'printenv'
      }
    }

//    stage('sonar-scan') {
//      steps {
//        sh "mvn clean compile verify sonar:sonar -Dsonar.projectKey=lumen -Dsonar.projectName='lumen' -Dsonar.host.url=http://10.144.233.86:9002 -Dsonar.token=sqp_be1a6c098d8a0c7da3bf60eb4ad2b9d489453c4b"
//      }
//    }

    stage('mvn build') {
      steps {
        sh "mvn clean compile package -Dmaven.test.skip=true"
      }
    }

    stage('image build and push') {
      steps {
        sh "docker build -t ${img_name.toLowerCase()}:${img_version.toLowerCase()} -f Dockerfile --build-arg ACTIVE=${PROFILE} ."
        sh "docker tag ${img_name.toLowerCase()}:${img_version.toLowerCase()} ${DOCKER_REGISTY}/sang/${img_name.toLowerCase()}:${img_version.toLowerCase()}"
        sh "docker tag ${img_name.toLowerCase()}:${img_version.toLowerCase()} ${DOCKER_REGISTY}/sang/${img_name.toLowerCase()}:latest"
        withCredentials([usernamePassword(credentialsId: 'docker-register', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUser')]) {
          sh "docker login -u ${dockerUser} -p ${dockerPassword} ${DOCKER_REGISTY}"
          sh "docker push ${DOCKER_REGISTY}/sang/${img_name.toLowerCase()}:${img_version.toLowerCase()}"
          sh "docker push ${DOCKER_REGISTY}/sang/${img_name.toLowerCase()}:latest"
        }
      }
    }


    stage('deploy') {
      steps {
        sh "cp k8s/deployment.yaml /opt/kubernetes/lumen/deployment-lumen.yaml"
        sh "kubectl delete -f /opt/kubernetes/lumen/deployment-lumen.yaml"
        sh "kubectl apply -f /opt/kubernetes/lumen/deployment-lumen.yaml"
      }
    }
  }
}
