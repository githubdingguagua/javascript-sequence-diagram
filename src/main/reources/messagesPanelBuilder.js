function MessagePanelBuilder(configuration, systemsBuilder, conversationReport, sequenceDiagramId, distancesCalculator) {

    var width = distancesCalculator.sequenceDiagramWidth()

    var panelHeight = configuration.distanceBetweenMessages * conversationReport.messages + configuration.distanceBetweenMessages

    function groupId() {
        return customisedIdWith("group")
    }

    function customisedIdWith(toBeDecorated) {
        return "messagePanelBuilder_" + sequenceDiagramId + "_" + toBeDecorated
    }

    function rectId() {
        return customisedIdWith("rect")
    }

    function leftUpperCornerXLocationOfSystem(systemBuilder) {
        return configuration.distanceFromLeftBorder + distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem(systemBuilder.name());
    }

    function drawConversationLineFor(aSystemBuilder, container) {
        var conversationLineXCoordinate = leftUpperCornerXLocationOfSystem(aSystemBuilder) + aSystemBuilder.width() / 2

        container.append("line")
            .attr("x1", conversationLineXCoordinate)
            .attr("y1", 0)
            .attr("x2", conversationLineXCoordinate)
            .attr("y2", panelHeight)
            .attr("fill", "#A80036")
            .attr("style", "stroke: #A80036; stroke-width: 1.0; stroke-dasharray: 2.0,2.0;")
    }

    function appendRectTo(mainContainer) {
        mainContainer.append("rect")
            .attr("id", rectId())
            .attr("x", 0)
            .attr("y", 0)
            .attr("width", width)
            .attr("height", height)
            .attr("fill", "#FEFECE")
            .attr("style", "stroke: blue; stroke-width: 1")
    }

    this.draw = function (svgContainer) {
        var mainContainer = svgContainer.append("g").attr("id", groupId())

        appendRectTo(mainContainer);

        systemsBuilder.forEach(function (aSystemBuilder) {
            drawConversationLineFor(aSystemBuilder, mainContainer)
        })

    }

}

