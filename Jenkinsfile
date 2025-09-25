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
          // пример для CI: -Dselenide.browser=chrome -Dselenide.headless=true
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

      // 2) Скачиваем JAR (если нет) и отправляем Slack
      sh '''
        set -e
        # Скачивание JAR в родительскую директорию workspace
        cd "$WORKSPACE/.."
        FILE=allure-notifications-4.8.0.jar
        if [ ! -f "$FILE" ]; then
          if command -v wget >/dev/null 2>&1; then
            wget -q https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
          else
            curl -L -o "$FILE" https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
          fi
        fi

        # Быстрая проверка, что summary.json реально существует там, куда указывает allureFolder
        cd "$WORKSPACE"
        REPORT_DIR="build/reports/allure-report/allureReport"
        if [ ! -f "$REPORT_DIR/widgets/summary.json" ]; then
          echo "❌ Не найден $REPORT_DIR/widgets/summary.json — Slack не отправим."
          echo "Проверь, что стадия ':allureReport' отработала и путь совпадает с base.allureFolder в notifications/config.json."
          exit 1
        fi

        # Запуск уведомления — БЕЗ '|| true', чтобы увидеть реальную ошибку (invalid_auth, not_in_channel и т.п.)
        echo "➡️  Отправляю Slack-уведомление. Канал из config.json."
        java "-DconfigFile=notifications/config.json" -jar ../allure-notifications-4.8.0.jar
      '''

      // 3) Уборка
      cleanWs(deleteDirs: true, notFailBuild: true)
    }

    success  { echo "✅ ${env.JOB_NAME} #${env.BUILD_NUMBER} SUCCESS" }
    unstable { echo "⚠️ ${env.JOB_NAME} #${env.BUILD_NUMBER} UNSTABLE — есть фейлы тестов" }
    failure  { echo "❌ ${env.JOB_NAME} #${env.BUILD_NUMBER} FAILURE" }
  }
}
