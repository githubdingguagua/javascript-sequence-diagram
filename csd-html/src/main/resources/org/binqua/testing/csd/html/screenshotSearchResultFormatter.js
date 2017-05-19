function ScreenshotSearchResultFormatter(smartBodyIndexer, screenshotsIndexer, searchResultLabelUtil, htmlIdManager) {

    var liIdPrefix = "screenshot-search-link"

    var that = this

    function liIdContent(elementIndex, searchResult){
        return "id='" + liIdPrefix + "_" +
                        searchResult.featureId + "_" +
                        htmlIdManager.screenshotImageId(searchResult.titleId, searchResult.scenarioId, searchResult.stepId) + "'"
    }

    function visualIndexOf(index){
        return index + 1
    }

    function featureLabel (elementIndex, smartBodyIndexer, configuration){
        return searchResultLabelUtil.featureLabel(visualIndexOf(elementIndex), configuration.featureId, smartBodyIndexer.featureNameById(configuration.featureId));
    }

    function scenarioLabel (smartBodyIndexer, configuration){
        return searchResultLabelUtil.scenarioLabel(configuration.scenarioId, smartBodyIndexer.scenarioNameById(configuration.scenarioId));
    }

    function stepLabel (smartBodyIndexer, configuration){
        return searchResultLabelUtil.stepLabel(configuration.stepId, smartBodyIndexer.stepNameById(configuration.stepId));
    }

    function screenshotTitleLabel (smartBodyIndexer, configuration){
        return searchResultLabelUtil.screenshotLabel(configuration.titleId,
                                                     screenshotsIndexer.screenshotPageTitleById({scenarioId: configuration.scenarioId, screenshotId: configuration.screenshotId}))
    }

    function htmlText (elementIndex, smartBodyIndexer, searchResult){
        return featureLabel(elementIndex, smartBodyIndexer, searchResult) +
              scenarioLabel(smartBodyIndexer, searchResult) +
              stepLabel(smartBodyIndexer, searchResult) +
              screenshotTitleLabel(smartBodyIndexer, searchResult)
    }

    function screenshotDataAttr (screenshotId){
        return "data-screenshot-id='" + screenshotId + "'"
    }

    function li (elementIndex, smartBodyIndexer, searchResult){
        return "<li " + screenshotDataAttr(searchResult.screenshotId) + " " + liIdContent(elementIndex, searchResult) + ">" +
                    "<a href='#" + htmlIdManager.screenshotsLabelId({scenarioId:searchResult.scenarioId, stepId: searchResult.stepId}) + "'>" +
                    htmlText(elementIndex, smartBodyIndexer, searchResult) +
                    "</a>" +
               "</li>"
    }

    function thumbnailTitleIdFrom (aStringWithANumber){
        return aStringWithANumber.replace(/^\D+/, '')
    }

    this.format = function (elementIndex, searchResult) {
        return li(elementIndex, smartBodyIndexer, searchResult)
    },

    this.makeIdClickable = function (someObject) {
        $('[id^=' + liIdPrefix + ']').click(function () {
            someObject.showFeature(that.toSearchScreenshotId(this.id));
            someObject.handleOpenSteps(that.toSearchScreenshotId(this.id));
        });
    },

    this.toSearchScreenshotId = function (idToBeParsed) {
       var idToBeParsedArray = idToBeParsed.split("_")
       return {object:{
                        id:idToBeParsedArray[1],
                        searchResult:{
                                       thumbnailsContainerId: htmlIdManager.thumbnailsContainerId({scenarioId:idToBeParsedArray[2], stepId: idToBeParsedArray[3]}),
                                       screenshotImageId: idToBeParsedArray[2] + "_" + idToBeParsedArray[3] + "_" + idToBeParsedArray[4]
                                     },
                        selectedStep: {featureId:idToBeParsedArray[1], scenarioId:idToBeParsedArray[2], stepId: idToBeParsedArray[3]}
                      }
              }
    }

}
