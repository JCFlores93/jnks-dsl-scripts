String basePath = 'JobsAsCode/example2'
String repo = 'git@github.com:JCFlores93/jenkins-ci-cd-go-hello-world.git'
String credentialSSH = 'JCFlores93'

folder(basePath) {
    description 'This example shows how to create a set of jobs for each github branch, each in its own folder.'
}

List branchesList = ['master','develop','feature']
branchesList.each { branch ->

    folder "$basePath/$branch"

    job("$basePath/$branch/example-build") {
        scm {
            git {
                remote {
                    url ("${repo}")
                    credentials ("${credentialSSH}")
                }
                branches ("$branch")
            }
        }
        triggers {
            scm 'H/30 * * * *'
        }
        steps {
            shell 'echo "assemble ..."'
        }
    }

    job("$basePath/$branch/example-deploy") {
        parameters {
            stringParam 'host'
        }
        steps {
            shell 'echo "deployin..."'
        }
    }
}

