pipeline {
  agent any

  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '30'))
    disableConcurrentBuilds()
  }

  triggers {
    // 02:00 Пн–Пт
    cron('0 2 * * 1-5')
  }

  environment {
    GRADLE_OPTS = '-Dorg.gradle.daemon=false -Dorg.gradle.console=plain'
    JAVA_TOOL_OPTIONS = '-Xms256m -Xmx1024m'
  }

  stages {

    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/main']],
          userRemoteConfigs: [[url: 'https://github.com/DaniyarElebesov/TestCases.git']],
          extensions: [[$class: 'CleanBeforeCheckout']]
        ])
        sh 'git rev-parse --short HEAD || true'
      }
    }

    stage('Prepare') {
      steps {
        sh '''
          chmod +x gradlew || true
          ./gradlew --version
        '''
      }
    }

    stage('Run Smoke Tests') {
      steps {
        sh './gradlew clean smokeTest --continue'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'build/test-results/**/*.xml'
          archiveArtifacts artifacts: 'build/allure-results/**, build/reports/**, build/logs/**', fingerprint: true, allowEmptyArchive: true
        }
      }
    }

    stage('Generate Allure Report') {
      steps {
        sh '''
          set +e
          ./gradlew allureReport
          EXIT=$?
          if [ $EXIT -ne 0 ]; then
            echo "No allureReport task or failed — continue with allure-results only"
          fi
          set -e
          ls -la build/allure-results || true
          ls -la allure-report || true
        '''
      }
    }

    stage('Download Allure Notifications Jar') {
      steps {
        sh '''
          cd ..
          FILE=allure-notifications-4.8.0.jar
          if [ ! -f "$FILE" ]; then
            if command -v wget >/dev/null 2>&1; then
              wget -q https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
            else
              curl -L -o allure-notifications-4.8.0.jar https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
            fi
          fi
          ls -la "$FILE"
        '''
      }
    }

    stage('Send Slack Notification') {
      steps {
        sh '''
          # запускаем jar из родительской директории, конфиг читаем из репо
          java "-DconfigFile=notifications/config.json" -jar ../allure-notifications-4.8.0.jar || true
        '''
      }
    }
  }

  post {
    always {
      // Публикация Allure в Jenkins (если плагин установлен)
      script {
        try {
          allure(results: [[path: 'build/allure-results']], reportBuildPolicy: 'ALWAYS')
        } catch (ignored) {
          echo 'Allure Jenkins Plugin не найден — пропускаю публикацию.'
        }
      }
      cleanWs(deleteDirs: true, notFailBuild: true)
    }
    success { echo '✅ Готово: smoke + Allure + Slack.' }
    unstable { echo '⚠️ UNSTABLE: есть фейлы, смотри отчёты.' }
    failure { echo '❌ FAILURE: смотри логи.' }
  }
}
