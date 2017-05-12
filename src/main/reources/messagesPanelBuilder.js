function MessagesPanelBuilder(configuration, systemsBuilder, conversationReport, sequenceDiagramId, distancesCalculator) {

    var systemNames = systemsBuilder.map(function (sb) {
        return sb.name()
    })

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

        function drawConversationLineFor(aSystemBuilder, mainContainer) {
            var conversationLineXCoordinate = distancesCalculator.middlePointXCoordinateOfSystem(aSystemBuilder.name())

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

    function appendMessagesTo(mainContainer, conversationReport) {

        function messageLine(aMessage, messageIndex) {

            var messageYCoordinate = (messageIndex + 1) * configuration.distanceBetweenMessages

            mainContainer.append("line")
                .attr("x1", distancesCalculator.middlePointXCoordinateOfSystem(aMessage.to))
                .attr("y1", messageYCoordinate)
                .attr("x2", distancesCalculator.middlePointXCoordinateOfSystem(aMessage.from))
                .attr("y2", messageYCoordinate)
                .attr("fill", "#A80036")
                .attr("style", "stroke: #A80036; stroke-width: 1.0; stroke-dasharray: 2.0,2.0;")
        }

        function messageArrow(aMessage, messageIndex) {
            var x1 = 0, points = "", delta = 10, y1 = (messageIndex + 1) * configuration.distanceBetweenMessages

            function pointAt(x, y) {
                return points.concat(x).concat(",").concat(y).concat(" ");
            }

            function messageIsFromLeftToRight(aMessage) {
                return systemNames.indexOf(aMessage.to) < systemNames.indexOf(aMessage.from)
            }

            if (messageIsFromLeftToRight(aMessage)) {
                x1 = distancesCalculator.middlePointXCoordinateOfSystem(aMessage.to)
            }else{
                x1 = distancesCalculator.middlePointXCoordinateOfSystem(aMessage.from)
                delta = -1 * delta
            }

            points = pointAt(x1 - delta, y1 - delta).pointAt(x1, y1).pointAt(x1 - delta, y1 + delta).pointAt(x1-delta+delta/2,y1).pointAt(x1 - delta, y1 - delta)
            mainContainer.append("polygon")
                .attr("points", points)
                .attr("fill", "#A80036")
                .attr("style", "stroke: #A80036; stroke-width: 1.0; stroke-dasharray: 2.0,2.0;")
        }

        conversationReport.messages.forEach(function (aMessage, messageIndex) {
            messageLine(aMessage, messageIndex);
            messageArrow(aMessage, messageIndex)
        })

    }

    this.draw = function (svgContainer) {
        var mainContainer = svgContainer.append("g").attr("id", groupId())

        appendRectTo(mainContainer, panelWidth, panelHeight)

        appendConversationLinesTo(mainContainer, systemsBuilder)

        appendMessagesTo(mainContainer, conversationReport)
    }

}

