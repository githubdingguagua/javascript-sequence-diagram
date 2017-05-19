function ConversationRenderer(namingUtil, htmlIdManager, screenshotNavigationRenderer, conversationMessagesRenderer) {

    this.toHtml =  function (stepConversation, featureToBeShown) {

        var that = this;

        function screenshotsHtml(stepConversation, featureToBeShown) {

            if (stepConversation.screenshots.length == 0){
                return "";
            }

            var result = "<div class='conversations-screenshots'>" +
                         "<p class='conversations-screenshots-title' id='" + htmlIdManager.screenshotsLabelId({scenarioId:stepConversation.scenario.id, stepId: stepConversation.step}) + "'>Screenshots: <span class='screenshots-count'>" + stepConversation.screenshots.length + " screenshots captured. </span></p>" +
                         "<div class='conversations-screenshots-body' id='" + htmlIdManager.screenshotsBodyId({scenarioId:stepConversation.scenario.id, stepId: stepConversation.step}) + "'>" +
                         "<div class='screenshots-content' id='" + htmlIdManager.thumbnailsContainerId({scenarioId:stepConversation.scenario.id, stepId: stepConversation.step}) + "'>" +
                         "<ul>";

            var numberOfScreenshots = stepConversation.screenshots.length;

            for (var i=0; i<numberOfScreenshots; i++) {
              var screenshot = stepConversation.screenshots[i],
                  screenshotTitleNumber = i + 1,
                  nextScreenshotId,
                  previousScreenshotId;

              nextScreenshotId = (screenshotTitleNumber < numberOfScreenshots) ? htmlIdManager.screenshotImageId(screenshotTitleNumber + 1, stepConversation.scenario.id, stepConversation.step) : false;
              previousScreenshotId = (i > 0) ? htmlIdManager.screenshotImageId(screenshotTitleNumber - 1, stepConversation.scenario.id, stepConversation.step) : false;


              result +=  "<li>" +
              "<div class='screenshot-container'>" +
                  "<p class='page-details'>Title " + screenshotTitleNumber + " : " + screenshot.title+ "</p>" +
                  "<p class='page-details'>Url: " + screenshot.url+ "</p>" +
                  "<p class='page-details'>Screenshot taken " + screenshot.browserEvent+ "</p>" +
                  "<a href='#'>" +
                    "<img data-screenshot-id='" + screenshot.id + "' " +
                      "id='" + htmlIdManager.screenshotImageId(screenshotTitleNumber, stepConversation.scenario.id, stepConversation.step) + "' " +
                      "data-next-screenshot='" + nextScreenshotId + "' " +
                      "data-previous-screenshot='" + previousScreenshotId + "' " +
                      "class='clickableScreenshot' " +
                      "src='" + namingUtil.thumbnailScreenshotImage(featureToBeShown, stepConversation.scenario.id, screenshot.id)+"'/>" +
                  "</a>" +
              "</div>" +
              "</li>"
            }

            return  result +
                    "</ul>" +
                    "</div>" +
                    screenshotNavigationRenderer.render({scenarioId: stepConversation.scenario.id, stepId: stepConversation.step, numberOfScreenshots: numberOfScreenshots}) +
                    "</div>" +
                    "</div>" ;
        }

        return  "<div class='conversations'>" +
                screenshotsHtml(stepConversation, featureToBeShown) +
                conversationMessagesRenderer.render(stepConversation.conversation) +
                "</div>";
    }

    this.attachScreenshots = function (featureConversationsByScenarios) {
       $.each(featureConversationsByScenarios, function( index, singleScenarioConversation ) {
           var stepIds = $.map( singleScenarioConversation.screenshots, function( stepScreenshots ) {return stepScreenshots.step});
           $.each(stepIds, function( index, theStepId ) {
              $("#" + htmlIdManager.thumbnailsContainerId({scenarioId:singleScenarioConversation.scenario.id, stepId: theStepId}) ).mThumbnailScroller({ type:"hover-70" });
           })
       })
    }
}
