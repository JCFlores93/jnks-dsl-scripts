String basePath = 'JobsAsCode/example3'

folder(basePath) {
    description 'This example shows how to use the configure block.'
}

job("$basePath/configure-block-example") {

    logRotator {
        numToKeep 5
    }

    triggers {
        scm 'H/5 * * * *'
    }

    steps {
        shell 'echo "Assemble..."'
    }

    configure { Node project ->
        project / builders / 'hudson.tasks.Shell' {
            command """
                echo 'Hola'
                pwd
                ls
            """
        }

        project / publishers / 'hudson.tasks.Mailer' {
            recipients 'academy@globant.com'
            dontNotifyEveryUnstableBuild 'false'
            sendToIndividuals 'false'
        }
    }
}