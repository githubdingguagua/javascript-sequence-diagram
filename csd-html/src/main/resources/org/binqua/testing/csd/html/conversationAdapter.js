function ConversationAdapter(stepConversationAdapter) {

    function findDistinctStepsInsideScenario(scenarioConversation) {

        var stepIdsInsideScreenshots = findStepIdsInScreenshots(scenarioConversation)

        var stepIdsInsideConversation = findStepIdsInConversation(scenarioConversation)

        return mergeRemovingDuplicateElements(stepIdsInsideScreenshots, stepIdsInsideConversation)
    }

    function mergeRemovingDuplicateElements(firstArray, secondArray){
        return firstArray.concat(secondArray.filter(function (item) {
            return firstArray.indexOf(item) < 0;
        }));
    }

    function findDistinctStepsInsideAConversation(featureConversationStructuredByScenarios) {

        var stepIdsInsideTheConversation = []

        $.each(featureConversationStructuredByScenarios, function( index, scenarioConversation ) {
               $.merge(stepIdsInsideTheConversation, findDistinctStepsInsideScenario(scenarioConversation))
            }
        )

        return stepIdsInsideTheConversation

    }

    function findStepIdsInScreenshots(scenarioConversation) {
       return  $.map(scenarioConversation.screenshots, function ( stepElement ){ return stepElement.step })
    }

    function findStepIdsInConversation(scenarioConversation) {
       return  $.map(scenarioConversation.conversation, function ( stepElement ){ return stepElement.step })
    }

    function findDistinctStepsInsideASingleScenarioConversation(singleScenarioConversation) {

        return mergeRemovingDuplicateElements(findStepIdsInScreenshots(singleScenarioConversation),
                                              findStepIdsInConversation(singleScenarioConversation))

    }

    this.adapt = function (featureConversationStructuredByScenarios) {

        var featureConversationStructuredBySteps = []

        $.each(featureConversationStructuredByScenarios, function(index, singleScenarioConversation) {
            var stepsInsideASingleScenarioConversation = findDistinctStepsInsideASingleScenarioConversation(singleScenarioConversation)

             $.each(stepsInsideASingleScenarioConversation, function( index, stepId ) {
                featureConversationStructuredBySteps.push(stepConversationAdapter.adapt(singleScenarioConversation, stepId))
             })

        })

        return featureConversationStructuredBySteps
    }

}

