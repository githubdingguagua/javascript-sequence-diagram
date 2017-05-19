function NamingUtil() {

    this.featureIndexFile = function (featureToBeShown) {
       return "features/"+featureToBeShown+"/index.html"
    },

    this.featureConversationFile = function (featureToBeShown) {
       return "features/"+featureToBeShown+"/conversation.json"
    },

    this.thumbnailScreenshotImage = function (featureToBeShown, scenarioId, imageIndex) {
       return "features/"+featureToBeShown+"/"+scenarioId+"/images/thumbnails/"+imageIndex+".png"
    },

    this.croppedScreenshotImage = function (featureToBeShown, scenarioId, imageIndex) {
        return "features/"+featureToBeShown+"/"+scenarioId+"/images/cropped/"+imageIndex+".png"
    },

    this.screenshotHtmlFile = function (featureToBeShown, scenarioId, imageIndex) {
        return "features/" + featureToBeShown + "/"+scenarioId+"/html/"+imageIndex+".html"
    },

    this.findScreenshot = function (featureConversations, scenarioIdToBeFound, screenshotIndexToBeFound) {
        var featureConversationFound = jQuery.grep(featureConversations, function( featureConversation, screenshotIndex ) {
            return ( featureConversation.scenario.id == scenarioIdToBeFound)
        })

        var allScenarioScreenshots = []
        $.each(featureConversationFound[0].screenshots, function( index, screenshotsByStep ) {
            allScenarioScreenshots = allScenarioScreenshots.concat(screenshotsByStep.data)
        })

        var screenshotsFound = jQuery.grep(allScenarioScreenshots, function( aSingleScreenshot, screenshotIndex ) {
            return ( aSingleScreenshot.id == screenshotIndexToBeFound )
        })

        return screenshotsFound[0]

    },

    this.featureReport = function (featureToBeShown) {
         return "features/"+featureToBeShown+"/featureReport.json"
    },

    this.scenarioScreenshots = function (featureToBeShown, scenarioId) {
         return "features/" + featureToBeShown + "/" + scenarioId + "/screenshots.json"
    }

}