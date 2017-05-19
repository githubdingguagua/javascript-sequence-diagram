function ScreenshotNavigationRenderer(htmlIdManager) {

    function htmlIdElementSuffixFrom(stepConversation) {
        return htmlIdElementSuffix(stepConversation.scenario.id, stepConversation.step)
    }

    function htmlIdElementSuffix(scenarioId, stepId) {
        return scenarioId + "_" + stepId
    }

    function thumbnailElementId(scenarioId, stepId) {
        return htmlIdManager.thumbnailsContainerId({scenarioId:scenarioId, stepId:stepId})
    }

    this.render =  function (stepConversation) {
        if (stepConversation.numberOfScreenshots <= 3){
            return ""
        }
        var dataId = htmlIdManager.generateId(stepConversation.scenarioId, stepConversation.stepId)
        return  "<div>" +
                "<span id='" + htmlIdManager.generateId("first-screenshot", stepConversation.scenarioId, stepConversation.stepId) + "' class='js-navigate-first btn-navigator previous' data-id='" + dataId + "'><span class='icon-container'><span class='icon'>&#9664;</span></span> Click here to go the first. </span>" +
                "<span id='" + htmlIdManager.generateId("last-screenshot", stepConversation.scenarioId, stepConversation.stepId) + "' class='js-navigate-last btn-navigator next' data-id='" + dataId + "'>Click here to go the last. <span class='icon-container'><span class='icon'>&#9654;</span></span></span>" +
                "<div class='clear-float padding-top'>Go to screenshot with title id: <input class='js-input-element' type='text' value=''/>" +
                   "<span data-id='" + dataId + "' class='btn-navigator js-navigate-carousel icon-container'>GO</span>" +
                "</div>" +
                "</div>"
    }

}
