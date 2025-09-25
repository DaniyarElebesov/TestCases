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
        // Фейлы тестов не ломают пайплайн — билд станет UNSTABLE
        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
          sh './gradlew clean smokeTest --continue'
          // Для CI можно так:
          // sh './gradlew clean smokeTest --continue -Dselenide.browser=chrome -Dselenide.headless=true -Dselenide.timeout=10000'
        }
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'build/test-results/**/*.xml'
          archiveArtifacts artifacts: 'build/allure-results/**, build/reports/**, build/logs/**',
                           fingerprint: true, allowEmptyArchive: true
        }
      }
    }

    // Необязательная стадия: gradle HTML-репорт (если есть таск)
    stage('Generate Allure Report (gradle)') {
      steps {
        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
          sh './gradlew allureReport || true'
        }
      }
    }
  }

  post {
    always {
      // 1) Публикация Allure в Jenkins — генерит HTML в $WORKSPACE/allure-report
      script {
        try {
          allure(results: [[path: 'build/allure-results']], reportBuildPolicy: 'ALWAYS')
        } catch (ignored) {
          echo 'Allure Jenkins Plugin не найден — пропускаю публикацию.'
        }
      }

      // 2) Slack-уведомление через allure-notifications (НЕ валит билд)
      script {
        // Ожидаем, что в notifications/config.json -> base.allureFolder = "./allure-report/"
        def reportDir = 'allure-report'
        def hasSummary = sh(returnStatus: true,
                            script: "test -f '${reportDir}/widgets/summary.json'") == 0
        if (!hasSummary) {
          echo "⚠️ Нет ${reportDir}/widgets/summary.json — пропущу Slack. " +
               "Убедись, что base.allureFolder = \"./allure-report/\" и публикация Allure прошла."
        } else {
          // Скачиваем JAR один раз в родительскую директорию
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
          // Запускаем уведомление; в случае ошибки — помечаем UNSTABLE и даём подсказку
          def rc = sh(returnStatus: true,
                      script: 'java "-DconfigFile=notifications/config.json" -jar ../allure-notifications-4.8.0.jar')
          if (rc != 0) {
            echo "⚠️ Allure Notifications rc=${rc}. Частые причины: invalid_auth (токен), " +
                 "channel_not_found / not_in_channel (канал), или сетевые ограничения (proxy/egress)."
            if (!currentBuild.result) currentBuild.result = 'UNSTABLE'
          } else {
            echo '✅ Slack-уведомление отправлено.'
          }
        }
      }

      // 3) Уборка
      cleanWs(deleteDirs: true, notFailBuild: true)
    }

    success  { echo "✅ ${env.JOB_NAME} #${env.BUILD_NUMBER} SUCCESS" }
    unstable { echo "⚠️ ${env.JOB_NAME} #${env.BUILD_NUMBER} UNSTABLE — смотри отчёты/уведомления" }
    failure  { echo "❌ ${env.JOB_NAME} #${env.BUILD_NUMBER} FAILURE" }
  }
}
