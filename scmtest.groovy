import hudson.Util
def backToNormalDuration(){
    def previousSuccessfulBuild = currentBuild.getPreviousSuccessfulBuild()
    if (null != previousSuccessfulBuild) {
        def initialFailureAfterPreviousSuccessfulBuild = previousSuccessfulBuild.getNextBuild()
        int initialFailureStartTime = initialFailureAfterPreviousSuccessfulBuild.getStartTimeInMillis()
        int initialFailureDuration = initialFailureAfterPreviousSuccessfulBuild.getDuration()
        int initialFailureEndTime = initialFailureStartTime + initialFailureDuration
        int buildStartTime = currentBuild.getStartTimeInMillis()
        int buildDuration = currentBuild.getDuration()
        int buildEndTime = buildStartTime + buildDuration
        int backToNormalDuration = buildEndTime - initialFailureEndTime
        return Util.getTimeSpanString(backToNormalDuration)
    }
    return null
}
pipeline {
    agent any

parameters {
  gitParameter branch: '', branchFilter: '.*', defaultValue: '', name: 'branch', quickFilterEnabled: true, selectedValue: 'NONE', sortMode: 'NONE', tagFilter: '*', type: 'GitParameterDefinition', useRepository: 'https://github.com/mdouek/smokeping.git'
}

    stages {
        stage('Hello') {
            steps {
                script {
                   echo backToNormalDuration()
                    }
            }
        }
    }
}