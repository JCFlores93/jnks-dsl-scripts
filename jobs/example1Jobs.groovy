String basePath = 'JobsAsCode/exampleA1'
String repo = 'git@github.globant.com:jheison-rodriguez/jenkins-ci-cd-go-hello-world.git'
String credentialSSH = 'gitlab_jenkins_jheison'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}

job("${basePath}/example-build") {
    scm {
        git {
            remote {
                url ("${repo}")
                credentials ("${credentialSSH}")
            }
            branches ("master")
        }
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell 'echo "assemble..."'
    }
}

job("${basePath}/example-deploy") {
    parameters {
        stringParam 'host'
    }
    steps {
        shell 'echo "deploying..."'
    }
}
