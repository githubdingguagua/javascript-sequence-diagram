function StepConversationAdapter() {

    function findScreenshotsWithStepId(singleScenarioConversationToBeAdapted, stepId) {
        var stepElementFound = $.grep(singleScenarioConversationToBeAdapted.screenshots, function(e){ return e.step == stepId; })
        return stepElementFound[0].data
    }

    function findConversationWithStepId(singleScenarioConversationToBeAdapted, stepId) {
        var stepElementFound = $.grep(singleScenarioConversationToBeAdapted.conversation, function(e){ return e.step == stepId; })
        return stepElementFound.length == 0 ? {"messages": [],"sequenceDiagram": ""} : stepElementFound[0]
    }

    this.adapt = function (singleScenarioConversationToBeAdapted, stepId) {
        var screenshots = findScreenshotsWithStepId(singleScenarioConversationToBeAdapted, stepId)
        var conversation = findConversationWithStepId(singleScenarioConversationToBeAdapted, stepId)
        return {
                "step": stepId,
                "scenario": singleScenarioConversationToBeAdapted.scenario,
                "screenshots": screenshots,
                "conversation": {
                    "messages": conversation.messages,
                    "sequenceDiagram": conversation.sequenceDiagram
                }
           };
    }

}

