function ScreenshotSearchResultHighlighter(colorToBeHighlightedWith, htmlIdManager) {

    var oldSelectedScreenshotData;

    function selectedScreenshotContainer (searchResult){
        return $("#" + searchResult.screenshotImageId).closest("div")
    }

    function changeSelectedScreenshotContainerColor (configuration){
        var searchResult = configuration.object.searchResult
        var newScreenshotContainer = selectedScreenshotContainer(searchResult)

        var screenshotContainerOriginalColor = newScreenshotContainer.css("border-color")
        newScreenshotContainer.css( { "border-color": colorToBeHighlightedWith} );

        return {
                   "configuration": configuration,
                   "originalBorderColor": screenshotContainerOriginalColor
               }
    }

    function restorePreviousScreenshotContainerColor (selectedScreenshotData){
        if (selectedScreenshotData === undefined){
            return
        }
        var oldScreenshotContainer = selectedScreenshotContainer(selectedScreenshotData.configuration.object.searchResult)
        oldScreenshotContainer.css( { "border-color" : selectedScreenshotData.originalBorderColor } );
    }

    function scrollThumbnail (configuration){

        showThumbnailContainer(configuration)

        var searchResult = configuration.object.searchResult;
        var thumbnail = $('#' + searchResult.thumbnailsContainerId);
        thumbnail.mThumbnailScroller("update");
        thumbnail.mThumbnailScroller("scrollTo", "#" + searchResult.screenshotImageId);
    }

    function showThumbnailContainer (configuration){
        $("#" + htmlIdManager.screenshotsBodyId({scenarioId:configuration.object.selectedStep.scenarioId, stepId: configuration.object.selectedStep.stepId})).show();
    }

     function notAValidConfiguration(configuration){
        return configuration.object === undefined ||
               configuration.object.searchResult === undefined
     }

     function isTheSameConfiguration(newConfiguration, oldConfiguration) {
         return newConfiguration.object.selectedScenario.featureId == oldConfiguration.object.selectedScenario.featureId &&
                newConfiguration.object.selectedScenario.scenarioId == oldConfiguration.object.selectedScenario.scenarioId &&
                newConfiguration.object.searchResult.screenshotImageId == oldConfiguration.object.searchResult.screenshotImageId
     }

    this.showSelectedSearchResult = function (newConfiguration) {

        if ( notAValidConfiguration(newConfiguration) ||
             (oldSelectedScreenshotData !== undefined && isTheSameConfiguration(newConfiguration, oldSelectedScreenshotData.configuration))){
            return
        }

        scrollThumbnail(newConfiguration)

        var selectedScreenshotData = changeSelectedScreenshotContainerColor(newConfiguration)

        restorePreviousScreenshotContainerColor(oldSelectedScreenshotData)

        oldSelectedScreenshotData = selectedScreenshotData

    },

    this.deselectOldSelection = function () {

        restorePreviousScreenshotContainerColor(oldSelectedScreenshotData)

        oldSelectedScreenshotData = undefined
    },

    this.handle = function (configuration) {
       return configuration.object !== undefined &&
              configuration.object.searchResult !== undefined &&
              configuration.object.searchResult.thumbnailsContainerId !== undefined &&
              configuration.object.searchResult.screenshotImageId !== undefined
    },

    this.isSearchResultSelected = function (configuration) {
        return configuration.object !== undefined &&
               configuration.object.searchResult !== undefined
    }

}