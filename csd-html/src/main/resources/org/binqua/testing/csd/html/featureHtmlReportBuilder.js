function FeatureHtmlReportBuilder(conversationRenderer, artifactLoader, conversationAdapter){

    function replaceAllStepPlaceholders(featureIndexHtmlWithStepPlaceholders, featureConversationStructuredBySteps, featureToBeShown) {
        $.each(featureConversationStructuredBySteps, function( index, singleStepConversation ) {
             var generatedStepConversationHtml = conversationRenderer.toHtml(singleStepConversation, featureToBeShown)

             var newHtmlData = $("<div>" + featureIndexHtmlWithStepPlaceholders + "</div>");
             newHtmlData.find('#' + singleStepConversation.step + "-details").replaceWith(generatedStepConversationHtml);

             featureIndexHtmlWithStepPlaceholders = newHtmlData.html()
        })
        return featureIndexHtmlWithStepPlaceholders
    }

    this.build = function (featureToBeShown, featureConversationStructuredByScenarios) {

        var featureIndexHtmlWithStepPlaceholders = artifactLoader.featureIndexFile(featureToBeShown)

        var featureConversationStructuredBySteps = conversationAdapter.adapt(featureConversationStructuredByScenarios)

        return replaceAllStepPlaceholders(
                featureIndexHtmlWithStepPlaceholders,
                featureConversationStructuredBySteps,
                featureToBeShown
        )
    }

    this.attachScreenshots = function (featureConversationsByScenarios) {
        conversationRenderer.attachScreenshots(featureConversationsByScenarios)
    }

}

