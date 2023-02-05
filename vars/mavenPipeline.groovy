def call(Map config) {
  // if config.type == 'dummy' {
  //   // ...
  // } else {
  //   // pipeline {...}
  // }
  pipeline {
    agent {
      node {
        label 'linux && java11'
      }
    }

    environment {
      AUTHOR  = 'John Doe'
      EMAIL   = 'johndoe@mail.com'
      WEB     = 'example.com'
    }

    options {
      disableConcurrentBuilds() // build jalan satu per satu
      timeout(time: 30, unit: 'MINUTES')
    }

    parameters {
      string(name: 'NAME', defaultValue: 'Guest', description: 'Your Name')
      text(name: 'DESCRIPTION', defaultValue: 'Guest', description: 'Tell me about you')
      booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Need to Deploy?')
      choice(name: 'SOCIAL_MEDIA', choices: ['Instagram', 'Facebook', 'Twitter'], description: 'Which social media?')
      password(name: 'SECRET', defaultValue: '', description: 'Encrypted key')
    }

    triggers {
      cron('0 0 * * *')
      // pollSCM('0 0 * * *')
      // upstream(upstreamProjects: 'job1,job2', threshold: hudson.model.Result.SUCCESS)
    }

    stages {
      // stage('Preparation') {
        // nested stages, pilih 'stages' atau 'steps', tidak bisa keduanya
        // stages {
        //   stage('prep#1') {
        //     echo('')
        //   }
        // }
        // ----------------------------------------------------------------
        // failFast true // by default, parallel tidak menghentikan stage jika salah satu error
        // parallel {
        //   stage('prep#1') {
        //     steps {
        //       sleep 10
        //       echo 'finish'
        //     }
        //   }
        //   stage('prep#2') {
        //     steps {
        //       sleep 10

        //       echo "NAME: ${params.NAME}"
        //       echo "DESCRIPTION: ${params.DESCRIPTION}"
        //       echo "DEPLOY: ${params.DEPLOY}"
        //       echo "SOCIAL_MEDIA: ${params.SOCIAL_MEDIA}"
        //       echo "SECRET: ${params.SECRET}"
        //     }
        //   }
        // }
        // ----------------------------------------------------------------
        // matrix {
        //   axes {
        //     axis {
        //       name 'OS'
        //       values 'linux', 'windows', 'osx'
        //     }
        //     axis {
        //       name 'ARC'
        //       values '32', '64'
        //     }
        //   }
        //   excludes {
        //     exclude {
        //       axis {
        //         name 'OS'
        //         values 'linux'
        //       }
        //       axis {
        //         name 'ARC'
        //         values '32'
        //       }
        //     }
        //     // exclude {...}
        //   }
        //   stages {
        //     stage('prep#1') {
        //       steps {
        //         echo "Start Job: ${OS} ${ARC}"
        //       }
        //     }
        //   }
        // }
      // }

      // stage('Build') {
      //   environment {
      //     TEST_CRED = credentials('havus_rahasia')
      //   }
      //   steps {
      //     echo 'Hello building...'
      //     echo "USERNAME: ${TEST_CRED_USR}"
      //     // echo "PASS: ${TEST_CRED_PSW}" // -> will be masked
      //     // sh('echo "PASS: $TEST_CRED_PSW"') // -> will be masked also
      //     sh('echo "PASS: $TEST_CRED_PSW" > "test_cred_1.txt"') // -> will be masked also
      //     echo "Start Job: ${env.JOB_NAME}"
      //     echo "Start Job: ${env.BUILD_NUMBER}"
      //     echo "Start Job: ${env.BRANCH_NAME}" // will null except new job with multi branch pipelinne
      //     // sh('./mvnw clean compile test-compile')
      //     echo 'Finish deploy'
      //   }
      // }

      // stage('Test') {
      //   steps {
      //     withCredentials([usernamePassword(
      //       credentialsId: 'havus_rahasia',
      //       usernameVariable: 'USER',
      //       passwordVariable: 'PASSWORD'
      //     )]) {
      //       echo 'Hello testing...'
      //       echo "test with cred ${USER} - ${PASSWORD}" // -> will be masked
      //       // sh('./mvnw test')
      //       echo 'Finish test'
      //       sh('echo "test with cred $USER - $PASSWORD" > "test_cred_2.txt"')
      //     }
      //   }
      // }

      // stage('Deploy') {
      //   input {
      //     // id 'default is stage name'
      //     message 'can we deploy?'
      //     ok 'yes of course!'
      //     submitter 'havus'
      //     parameters {
      //       choice(name: 'TARGET_ENV', choices: ['Staging', 'Sandbox', 'Production'], description: 'Which environment?')
      //     }
      //   }

      //   when {
      //     expression {
      //       return params.DEPLOY
      //     }
      //   }

      //   // agent {
      //   //   node {
      //   //     label 'linux && java11'
      //   //   }
      //   // }

      //   steps {
      //     echo 'Hello deploying...'
      //     echo "Environment ${TARGET_ENV}"

      //     script {
      //       def data = [
      //         'id': env.BUILD_NUMBER,
      //         'firstName': 'John',
      //         'lastName': 'Doe'
      //       ]

      //       for (int i = 0; i < 10; i++) {
      //         echo "Number ${i}"
      //       }

      //       writeJSON(file: 'data.json', json: data)
      //     }
      //   }
      // }

      stage('Shrlib') { // learn shared library
        steps {
          script {
            // <nama-flie>.<func-name>
            hello.sayHello()
            Output.sayHello('John')
            Output.sayHelloWithStep(this, 'John')
            echo("${author.title()} ${author.name()}")
            echo("call: ${author()}")
            hello.sayHelloToAll(["John", "Doe", "Maverick"])
            hello.sayHelloToPerson([
              firstName: 'Budi',
              lastName: 'Setiawan',
            ])

            def config = libraryResource("config/build.json")
            echo(config)
          }
        }
      }
    }
    post {
      always {
        echo 'always post'
      }
      success {
        echo 'success post'
      }
      failure {
        echo 'failure post'
      }
      cleanup {
        echo 'cleanup post'
      }
    }
  }
}