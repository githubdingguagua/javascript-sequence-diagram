function HtmlIdManager() {

    const separator = "_"

    function toScenarioAndStepIds(input){
        var result = input.match(/(scenario-\d+).*(step-\d+)/)
        if (result == null){
            throw new Error("id has to contains regex (scenario-\d+).*(step-\d+)");
        }
        return result
    }

    this.screenshotImageId = function (titleId, arg1, stepId) {
        function toId(titleId, scenarioId, stepId){
            return scenarioId + separator + stepId + separator + "title-" + titleId
        }
        if(arguments.length == 3) {
            return toId(titleId, arg1, stepId)
        }
        if(arguments.length == 2) {
            var result = toScenarioAndStepIds(arg1)
            return toId(titleId, result[1], result[2])
        }
        throw new Error("Use at least 2 args");
    }

    this.generateId = function () {
        function toArray(arguments){
            return Array.prototype.slice.call(arguments)
        }
        return toArray(arguments).join(separator);
    }

    this.scenarioId = function (toBeParsed) {
        return toBeParsed.split(separator)[0]
    }

    this.screenshotId = function (toBeParsed) {
        return toBeParsed.split(separator)[2].replace(/screenshot-/i, "")
    }

    this.screenshotsLabelId = function (configuration) {
        return "screenshots-label" + separator + configuration.scenarioId + separator + configuration.stepId
    }

    this.screenshotsBodyId = function (configuration) {
        return "screenshots-body" + separator + configuration.scenarioId + separator + configuration.stepId
    }

    this.thumbnailsContainerId = function (configuration) {
        function toId(scenarioId, stepId){
            return "thumbnail" + separator + scenarioId + separator + stepId
        }
        if (typeof configuration === "string"){
            var result = toScenarioAndStepIds(configuration)
            if (result != null){
                return toId(result[1], result[2])
            }
        }
        return toId(configuration.scenarioId, configuration.stepId)
    }

}

