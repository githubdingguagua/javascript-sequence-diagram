function MessagesPanelBuilder(configuration, systemsBuilder, conversationReport, sequenceDiagramId, distancesCalculator) {

    var panelWidth = distancesCalculator.sequenceDiagramWidth()

    var panelHeight = configuration.distanceBetweenMessages * conversationReport.messages.length + configuration.distanceBetweenMessages

    function groupId() {
        return customisedIdWith("group")
    }

    function customisedIdWith(toBeDecorated) {
        return "messagePanelBuilder_" + sequenceDiagramId + "_" + toBeDecorated
    }

    function rectId() {
        return customisedIdWith("rect")
    }

    function appendRectTo(mainContainer, panelWidth, panelHeight) {
        mainContainer.append("rect")
            .attr("id", rectId())
            .attr("x", 0)
            .attr("y", 0)
            .attr("width", panelWidth)
            .attr("height", panelHeight)
            .attr("fill", "#FEFECE")
            .attr("style", "stroke: blue; stroke-width: 1")
    }

    function appendConversationLinesTo(mainContainer, systemsBuilder) {

        function leftUpperCornerXLocationOfSystem(systemBuilder) {
            return configuration.distanceFromLeftBorder + distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem(systemBuilder.name());
        }

        function drawConversationLineFor(aSystemBuilder, mainContainer) {
            var conversationLineXCoordinate = leftUpperCornerXLocationOfSystem(aSystemBuilder) + aSystemBuilder.width() / 2

            mainContainer.append("line")
                .attr("x1", conversationLineXCoordinate)
                .attr("y1", 0)
                .attr("x2", conversationLineXCoordinate)
                .attr("y2", panelHeight)
                .attr("fill", "#A80036")
                .attr("style", "stroke: #A80036; stroke-width: 1.0; stroke-dasharray: 2.0,2.0;")
        }

        systemsBuilder.forEach(function (aSystemBuilder) {
            drawConversationLineFor(aSystemBuilder, mainContainer)
        })
    }

    this.draw = function (svgContainer) {
        var mainContainer = svgContainer.append("g").attr("id", groupId())

        appendRectTo(mainContainer, panelWidth, panelHeight);
        appendConversationLinesTo(mainContainer, systemsBuilder);
    }

}

