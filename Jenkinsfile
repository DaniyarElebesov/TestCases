pipeline {
  agent any

  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '30'))
    disableConcurrentBuilds()
    skipDefaultCheckout(true)
  }

  // 02:00 Пн–Пт
  triggers { cron('0 2 * * 1-5') }

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
        // НЕ роняем пайплайн на фейлах: билд станет UNSTABLE, но пойдём дальше
        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
          sh './gradlew clean smokeTest --continue'
          // При необходимости:
          // sh './gradlew clean smokeTest --continue -Dselenide.browser=chrome -Dselenide.headless=true -Dselenide.timeout=10000'
        }
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'build/test-results/**/*.xml'
          archiveArtifacts artifacts: 'build/allure-results/**, build/reports/**, build/logs/**', fingerprint: true, allowEmptyArchive: true
        }
      }
    }

    stage('Generate Allure Report (gradle)') {
      steps {
        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
          sh '''
            set +e
            ./gradlew allureReport
            exit 0
          '''
        }
      }
    }
  }

  post {
    always {
      // 1) Публикуем Allure (читает build/allure-results)
      script {
        try {
          allure(results: [[path: 'build/allure-results']], reportBuildPolicy: 'ALWAYS')
        } catch (ignored) {
          echo 'Allure Jenkins Plugin не найден — пропускаю публикацию.'
        }
      }

      // 2) Готовим JAR и отправляем уведомление в Slack
      script {
        // Скачивание JAR (если ещё нет)
        sh '''
          cd "$WORKSPACE/.."
          FILE=allure-notifications-4.8.0.jar
          if [ ! -f "$FILE" ]; then
            if command -v wget >/dev/null 2>&1; then
              wget -q https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
            else
              curl -L -o "$FILE" https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
            fi
          fi
        '''

        // Пробуем достать токен из Jenkins Credentials,
        // если нет — тихо падаем в catch и используем токен из файла репозитория
        try {
          withCredentials([string(credentialsId: 'slack-bot-token', variable: 'SLACK_BOT_TOKEN')]) {
            if (env.SLACK_BOT_TOKEN?.trim()) {
              sh '''
                cd "$WORKSPACE"
                # Осторожно подменяем token в notifications/config.json на секрет из Jenkins
                sed -i.bak -E 's/"token"\\s*:\\s*".*"/"token": "'$SLACK_BOT_TOKEN'"/' notifications/config.json
                # Запуск уведомления
                java "-DconfigFile=notifications/config.json" -jar ../allure-notifications-4.8.0.jar || true
              '''
            } else {
              echo "Jenkins credential 'slack-bot-token' пуст — использую токен из репозитория."
              sh 'cd "$WORKSPACE"; java "-DconfigFile=notifications/config.json" -jar ../allure-notifications-4.8.0.jar || true'
            }
          }
        } catch (err) {
          echo "Jenkins credential 'slack-bot-token' не найден — использую токен из репозитория."
          sh 'cd "$WORKSPACE"; java "-DconfigFile=notifications/config.json" -jar ../allure-notifications-4.8.0.jar || true'
        }
      }

      // 3) Уборка
      cleanWs(deleteDirs: true, notFailBuild: true)
    }

    success  { echo "✅ ${env.JOB_NAME} #${env.BUILD_NUMBER} SUCCESS" }
    unstable { echo "⚠️ ${env.JOB_NAME} #${env.BUILD_NUMBER} UNSTABLE — есть фейлы тестов" }
    failure  { echo "❌ ${env.JOB_NAME} #${env.BUILD_NUMBER} FAILURE" }
  }
}
