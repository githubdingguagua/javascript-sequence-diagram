var configuration = {
    "defaultDistanceBetweenSystems": 50,
    "distanceFromHorizontalBorder": 10,
}

function DistancesCalculator() {
    this.leftUpperCornerDistanceBetweenFirstSystemAndSystem = function () {
        return width
    }
}

var conversationAnalyserResult = {
    "systemNames":["A","B","C"],
    "maxDescriptionLength":"",
    "conversation":[{"from":"A","to":"B","description":"this is a message"}]
}

var svgContainer = d3.select("#svgElement")

QUnit.test("",
    function (assert) {

        var messageDescriptionBuilder = new MessagesDescriptionBuilder(configuration, "sequenceDiagramId", new DistancesCalculator(), conversationAnalyserResult)

        messageDescriptionBuilder.draw(svgContainer)

    }
)

