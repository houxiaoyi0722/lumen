pipeline {
  agent any

  environment {
    PROFILE = "txy"
    DOCKER_REGISTY = "10.144.233.86:5000"
    pom = readMavenPom file: 'pom.xml'
    img_name = "${pom.artifactId}-${pom.version}-${PROFILE}"
    img_version = "${pom.version}"
  }

  stages {

    stage('prepare') {
      steps {
        sh 'printenv'
      }
    }

    stage('mvn build') {
      steps {
        sh "mvn clean package"
      }
    }

    stage('image build and push') {
      steps {
        sh "docker build -t ${img_name}:${img_version} -f Dockerfile --build-arg PROFILE=${PROFILE}"
        sh "docker tag ${img_name}:${img_version} ${DOCKER_REGISTY}/${img_name}:${img_version}"
        sh "docker push ${DOCKER_REGISTY}/${img_name}:${img_version}"
//        withCredentials([usernamePassword(credentialsId: 'docker-register', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUser')]) {
//          sh "docker login -u ${dockerUser} -p ${dockerPassword} docker.***.com"
//          sh "docker push ${docker_img_name}:latest"
//          sh "docker push ${docker_img_name}:${pom.version}"
//          sh "docker push ${docker_img_name}:${build_tag}"
//        }
      }
    }

    //stage("部署到远端服务") {
    //  steps {
    //    script {
    //      def remoteConfig = [:]
    //      remoteConfig.name = "my-remote-server"
    //      remoteConfig.host = "${REMOTE_HOST}"
    //      remoteConfig.port = "${REMOTE_SSH_PORT}".toInteger()
    //      remoteConfig.allowAnyHosts = true
    //
    //      withCredentials([
    //        sshUserPrivateKey(
    //          credentialsId: "${REMOTE_CRED}",
    //          keyFileVariable: "privateKeyFilePath"
    //        ),
    //        usernamePassword(
    //          credentialsId: "${CODING_ARTIFACTS_CREDENTIALS_ID}",
    //          usernameVariable: 'CODING_DOCKER_REG_USERNAME',
    //          passwordVariable: 'CODING_DOCKER_REG_PASSWORD'
    //        )
    //      ]) {
    //        // SSH 登录用户名
    //        remoteConfig.user = "${REMOTE_USER_NAME}"
    //        // SSH 私钥文件地址
    //        remoteConfig.identityFile = privateKeyFilePath
    //
    //        // 请确保远端环境中有 Docker 环境
    //        sshCommand(
    //          remote: remoteConfig,
    //          command: "docker login -u ${CODING_DOCKER_REG_USERNAME} -p ${CODING_DOCKER_REG_PASSWORD} ${CODING_DOCKER_REG_HOST}",
    //          sudo: true,
    //        )
    //
    //        sshCommand(
    //          remote: remoteConfig,
    //          command: "docker rm -f java-spring-app | true",
    //          sudo: true,
    //        )
    //
    //        // DOCKER_IMAGE_VERSION 中涉及到 GIT_LOCAL_BRANCH / GIT_TAG / GIT_COMMIT 的环境变量的使用
    //        // 需要在本地完成拼接后，再传入到远端服务器中使用
    //        DOCKER_IMAGE_URL = sh(
    //          script: "echo ${CODING_DOCKER_REG_HOST}/${CODING_DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION}",
    //          returnStdout: true
    //        )
    //
    //        sshCommand(
    //          remote: remoteConfig,
    //         command: "docker run -d -p 8080:8080 --name java-spring-app ${DOCKER_IMAGE_URL}",
    //          sudo: true,
    //       )
    //
    //        echo "部署成功，请到 http://${REMOTE_HOST}:8080 预览效果"
    //      }
    //    }
    //  }
    //}
  }
}
