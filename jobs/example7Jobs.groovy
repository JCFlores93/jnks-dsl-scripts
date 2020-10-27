String basePath = 'JobsAsCode/example7'
List developers = ['dev1@example.com', 'dev2@example.com']

folder(basePath) {
    description 'This example shows how to create jobs using Job builders.'
}

new GradleCiJobBuilder()
    .name("$basePath/gradle-project1")
    .description('An example using a job builder for a Gradle project.')
    .ownerAndProject('myorg/project1')
    .emails(developers)
    .build(this)

new GradleCiJobBuilder()
    .name("$basePath/gradle-project2")
    .description('Another example using a job builder for a Gradle project.')
    .ownerAndProject('myorg/project2')
    .emails(developers)
    .build(this)




/////////////////////////////////////////////////////////////////////////////77


import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Example Class for creating a Gradle build
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GradleCiJobBuilder {

    String name
    String description
    String ownerAndProject
    String gitBranch = 'master'
    String pollScmSchedule = '@daily'
    String tasks
    String switches
    Boolean useWrapper = true
    String junitResults = '**/build/test-results/*.xml'
    String artifacts = '**/build/libs/*.jar'
    List<String> emails = []

    Job build(DslFactory dslFactory) {
        dslFactory.job(name) {
            it.description this.description
            logRotator {
                numToKeep 5
            }
            scm {
                github this.ownerAndProject, gitBranch
            }
            triggers {
                scm pollScmSchedule
            }
            steps {
                gradle tasks, switches, useWrapper
            }
            publishers {
                archiveArtifacts artifacts
                archiveJunit junitResults
                if (emails) {
                    mailer emails.join(' ')
                }
            }
        }
    }
}

//////////////////////////////////////////////////////////

