function SmartBodyIndexer(namingUtil, featureList) {

    this.index = lunr(function () {
        this.field('text')
        this.ref('id')
    })

    this.featureIdAndTitleList = []

    this.scenarioIdAndTitleList = []

    this.stepsIdAndTextList = []

    var that = this

    var idSeparator = "@@"

    function loadJsonFileSynch(fileToBeLoaded, featureId) {
        $.ajax({
            url: fileToBeLoaded,
            async: false,
            dataType: 'json',
            success: function (featureReport) {

                function lunrIdForFeature (featureId) {
                    return featureId
                }

                function lunrIdForScenario (featureId, scenario) {
                    return featureId + idSeparator + scenario.id
                }

                function lunrIdForStep (featureId, scenario, step) {
                    return featureId + idSeparator + scenario.id + idSeparator + step.id
                }

                that.index.add({
                    id: lunrIdForFeature(featureId),
                    text: featureReport.title
                })

                that.featureIdAndTitleList.push({id:featureId, text:featureReport.title})

                $.each(featureReport.scenarios, function( index, scenario ) {
                     that.index.add({
                                id: lunrIdForScenario(featureId, scenario),
                                text: scenario.title
                     })

                     that.scenarioIdAndTitleList.push({id:scenario.id, text:scenario.title})

                     $.each(scenario.steps, function( index, step ) {

                          that.stepsIdAndTextList.push({id:step.id, text:step.key + " - " + step.text})

                          var toBeAdded = {
                                           id: lunrIdForStep(featureId, scenario, step),
                                           text: step.text
                          }

                          that.index.add(toBeAdded)
                     })

                })

            }
        });
    }

    this.init = function () {
        $.each(featureList, function( index, feature ) {
            loadJsonFileSynch(namingUtil.featureReport(feature.id), feature.id)
        })
    }

    this.search = function (toBeFound) {
        function toSearchResultId(idToBeParsed) {
           function emptyIfUndefined (value) {
                return value ? value : ""
           }
           var idToBeParsedArray = idToBeParsed.split(idSeparator)
           return {
                    featureId:idToBeParsedArray[0],
                    scenarioId:emptyIfUndefined(idToBeParsedArray[1 ]),
                    stepId:emptyIfUndefined(idToBeParsedArray[2])
                  }
        }
        return $.map(this.index.search(toBeFound), function (elementFound){ return toSearchResultId(elementFound.ref)})
    }

    this.featureNameById = function (featureId) {
        return $.grep(this.featureIdAndTitleList, function( featureIdAndTitlePair ) { return featureIdAndTitlePair.id == featureId})[0].text
        throw "featureId " + featureId + " not found in " + JSON.stringify(this.featureIdAndTitleList)
    }

    this.scenarioNameById = function (scenarioId) {
        return $.grep(this.scenarioIdAndTitleList, function( scenarioIdAndTitlePair ) { return scenarioIdAndTitlePair.id == scenarioId})[0].text
        throw "scenarioId " + scenarioId + " not found in " + JSON.stringify(this.scenarioIdAndTitleList);
    }

    this.stepNameById = function (stepId) {
        return $.grep(this.stepsIdAndTextList, function( stepIdAndTextPair ) { return stepIdAndTextPair.id == stepId})[0].text
        throw "stepId " + stepId + " not found in " + JSON.stringify(this.stepsIdAndTextList);
    }

}

