pipeline {
   agent any
   
   environment {
      GIT_CREDENTIALS = credentials('github')
   }
   
   tools {
      jdk 'JAVA_HOME'
      maven 'Maven'
   }
   
   stages {
      stage('Checkout') {
         steps {
            echo 'Cloning repository...'
            git branch: 'main',
            url: 'https://github.com/PremEvantech/TestngAutomationFramework',
            credentialsId: 'github'
         }
      }
      
      stage('Verify Tools') {
         steps {
            echo 'Checking Java and Maven versions...'
            bat 'java -version'
            bat 'mvn -version'
         }
      }
      
      stage('Build & Test') {
         steps {
            echo 'Running Maven clean test...'
            bat 'mvn clean test'
         }
      }
      
      stage('Publish TestNG Results') {
         steps {
            junit '**/target/surefire-reports/*.xml'
         }
      }
      
      stage('Archive TestNG HTML Report') {
         steps {
            archiveArtifacts artifacts: 'test-output/**', fingerprint: true
         }
      }
   }
   
   post {
      always {
         script {
            def reportFiles = findFiles(glob: 'reports/*.html')
            if (reportFiles.length > 0) {
               archiveArtifacts artifacts: 'reports/*.html', allowEmptyArchive: true
               publishHTML([
               allowMissing: false,
               alwaysLinkToLastBuild: true,
               keepAll: true,
               reportDir: 'reports',
               reportFiles: '*.html',
               reportName: 'Extent Reports'
               ])
            } else {
               echo "No Extent reports found to archive!"
            }
         }
      }
      
      success {
         echo 'Build Successful!'
      }
      failure {
         echo 'Build Failed! Check logs for errors.'
      }
   }
}