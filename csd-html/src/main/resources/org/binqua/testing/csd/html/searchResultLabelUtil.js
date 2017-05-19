function SearchResultLabelUtil() {

     this.featureLabel =  function (indexResultToBeShown, featureId, featureDescription) {
        return "<span class='search-result-feature'>" + indexResultToBeShown + ") Feature " + parseInt(featureId.replace("feature-","")) + ":</span> <span>" + featureDescription + "</span>"
     },

     this.scenarioLabel =  function (scenarioId, scenarioDescription) {
        return "<div><span class='search-result-scenario'>Scenario " + parseInt(scenarioId.replace("scenario-","")) + ":</span> <span>" + scenarioDescription  + "</span></div>"
     },

     this.stepLabel =  function (stepId, stepDescription) {
        return "<div class='search-result-step-container'><span class='search-result-step'>Step " + parseInt(stepId.replace("step-","")) + ":</span> <span>" + stepDescription  + "</span></div>"
     },

     this.screenshotLabel =  function (titleId, screenshotDescription) {
        return "<div class='search-result-screenshot-container'><span class='search-result-screenshot'>Title "+ parseInt(titleId) + ":</span> <span>" + screenshotDescription  + "</span></div>"
     }
}
