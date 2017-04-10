function MessagesDescriptionBuilder(configuration, sequenceDiagramId, distancesCalculator, conversation) {

    this.draw = function (svgContainer) {

        conversation.forEach(function (aSystemBuilder) {
            aSystemBuilder.draw(mainContainer)
            translate(aSystemBuilder, leftUpperCornerXLocationOfSystem(aSystemBuilder), configuration.distanceFromUpperBorder)
            drawConversationLine(aSystemBuilder, mainContainer)
        })

    }

}

