String basePath = 'JobsAsCode/example5'

folder(basePath) {
    description 'This example shows how to pull out common components into static methods.'
}

job("$basePath/static-method-example1") {

    steps {
        gradle {
            tasks 'clean test'
            useWrapper true
            switches '''
                -Dhttp.proxyHost=proxy.example.com
                -Dhttps.proxyHost=proxy.example.com
                -Dhttp.proxyPort=80
                -Dhttps.proxyPort=80
            '''
        }
    }
}

job("$basePath/static-method-example2") {

    steps {
        StepsUtil.proxiedGradle (delegate, 'clean test 2')
    }
}



class StepsUtil {
    static void proxiedGradle(context, String gradleTasks) {
        context.with {
            gradle {
                useWrapper true
                tasks gradleTasks
                switches '''
                    -Dhttp.proxyHost=xxx
                    -Dhttps.proxyHost=xxx
                    -Dhttp.proxyPort=xxx
                    -Dhttps.proxyPort=xxx
                '''.stripIndent().trim()
            }
        }
    }
}

job("$basePath/static-method-example3") {

    steps {
        StepsUtil.proxiedGradle (delegate, 'clean test 3')
    }
}

job("$basePath/static-method-example4") {

    steps {
        StepsUtil.proxiedGradle (delegate, 'clean test 4')
    }
}