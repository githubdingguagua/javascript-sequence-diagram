function BodySearchResultFormatter(smartBodyIndexer, searchResultLabelUtil) {

    var liIdPrefix = "body-search-link"

    var that = this

    function idFrom (configuration, index){
        return liIdPrefix + "_" + configuration.featureId + scenarioIdPart(configuration) + stepIdPart(configuration)
    }

    function liIdAttribute (configuration, index){
        return "id='" + idFrom(configuration, index) + "'"
    }

    function scenarioIdPart (configuration){
        if (configuration.scenarioId){
            return "_" + configuration.scenarioId
        }
        return ""
    }

    function stepIdPart (configuration){
        if (configuration.stepId){
            return "_" + configuration.stepId
        }
        return ""
    }

    function featureLabel (index, smartBodyIndexer, configuration){
        return searchResultLabelUtil.featureLabel(index + 1 , configuration.featureId, smartBodyIndexer.featureNameById(configuration.featureId))
    }

    function scenarioLabel (smartBodyIndexer, configuration){
        if (configuration.scenarioId){
            return searchResultLabelUtil.scenarioLabel(configuration.scenarioId, smartBodyIndexer.scenarioNameById(configuration.scenarioId))
        }
        return ""
    }

    function stepLabel (smartBodyIndexer, configuration){
       if (configuration.stepId){
            return searchResultLabelUtil.stepLabel(configuration.stepId, smartBodyIndexer.stepNameById(configuration.stepId))
       }
       return ""
    }

    function anchorIdFrom (configuration){
       if (configuration.stepId){
            return configuration.stepId
       }
       if (configuration.stepId == "" && configuration.scenarioId){
            return configuration.scenarioId
       }
       if (configuration.stepId == "" && configuration.scenarioId == "" && configuration.featureId){
            return configuration.featureId
       }
    }

    function li (smartBodyIndexer, configuration, index){
        return "<li " + liIdAttribute(configuration, index) + "><a href='#" + anchorIdFrom( configuration ) + "'>" +
                     featureLabel(index, smartBodyIndexer, configuration) +
                     scenarioLabel(smartBodyIndexer, configuration) +
                     stepLabel(smartBodyIndexer, configuration) +
               "</a></li>"
    }

    this.format = function (index, configuration) {
        return li(smartBodyIndexer, configuration, index)
    },

    this.makeIdClickable = function (someObject) {
        $('[id^=' + liIdPrefix + ']').click(function () {
            someObject.showFeature(that.toSearchResultId(this.id));
            someObject.handleOpenSteps(that.toSearchResultId(this.id));
        });
    },

    this.toSearchResultId = function (idToBeParsed) {

        function takeTheLastElementFrom (idToBeParsedArray){
            return idToBeParsedArray[idToBeParsedArray.length - 1]
        }

        var idToBeParsedArray = idToBeParsed.split("_")
        return {object:{id:idToBeParsedArray[1], searchResult:{idFound:takeTheLastElementFrom(idToBeParsedArray)}}}
     }

}

