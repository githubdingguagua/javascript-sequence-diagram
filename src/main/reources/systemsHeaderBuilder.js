function SystemsHeaderBuilder(configuration, systemBuilders, sequenceDiagramId, distancesCalculator) {

    var width = distancesCalculator.sequenceDiagramWidth()

    var height = configuration.distanceFromUpperBorder + systemBuilders[0].height() + configuration.distanceFromUpperBorder

    function groupId() {
        return customisedIdWith("group")
    }

    function customisedIdWith(toBeDecorated) {
        return "systemHeaderBuilder_" + sequenceDiagramId + "_" + toBeDecorated
    }

    function rectId() {
        return customisedIdWith("rect")
    }

    function leftUpperCornerXLocationOfSystem(systemBuilder) {
        return configuration.distanceFromLeftBorder + distancesCalculator.leftUpperCornerDistanceBetweenFirstSystemAndSystem(systemBuilder.name());
    }

    function translate(aSystemBuilder, x, y) {
        d3.select(aSystemBuilder.selector()).attr("transform", "translate(" + x + "," + y + ")")
    }

    function drawConversationLine(aSystemBuilder, container) {
        var conversationLineXCoordinate = leftUpperCornerXLocationOfSystem(aSystemBuilder) + aSystemBuilder.width() / 2

        container.append("line")
            .attr("x1", conversationLineXCoordinate)
            .attr("y1", configuration.distanceFromUpperBorder + aSystemBuilder.height())
            .attr("x2", conversationLineXCoordinate)
            .attr("y2", configuration.distanceFromUpperBorder + aSystemBuilder.height() + configuration.distanceFromUpperBorder)
            .attr("fill", "#A80036")
            .attr("style", "stroke: #A80036; stroke-width: 1.0; stroke-dasharray: 2.0,2.0;")
    }

    this.draw = function (svgContainer) {
        var mainContainer = svgContainer.append("g").attr("id", groupId())

        mainContainer.append("rect")
            .attr("id", rectId())
            .attr("x", 0)
            .attr("y", 0)
            .attr("width", width)
            .attr("height", height)
            .attr("fill", "#FEFECE")
            .attr("style", "stroke: blue; stroke-width: 1")

        systemBuilders.forEach(function (aSystemBuilder) {
            aSystemBuilder.draw(mainContainer)
            translate(aSystemBuilder, leftUpperCornerXLocationOfSystem(aSystemBuilder), configuration.distanceFromUpperBorder)
            drawConversationLine(aSystemBuilder, mainContainer)
        })

    }

}

