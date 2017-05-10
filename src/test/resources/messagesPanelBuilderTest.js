var configuration = {distanceBetweenMessages:30, distanceFromLeftBorder:10 }

var svgContainer = d3.select("#svgElement")

function SystemBuilder(name, width) {
    this.width = function () {
        return width
    }
    this.name = function () {
        return name
    }
}

var systemsBuilder = [new SystemBuilder("A", 50 + 50), new SystemBuilder("B", 50 + 50), new SystemBuilder("C", 50 + 50)]

function DistancesCalculator() {

    this.sequenceDiagramWidth = function () {
        return 1500
    }

    this.leftUpperCornerDistanceBetweenFirstSystemAndSystem = function (nameOfTheSystemToAnalyse) {
        if (nameOfTheSystemToAnalyse == "A") return 0
        if (nameOfTheSystemToAnalyse == "B") return 200
        if (nameOfTheSystemToAnalyse == "C") return 400
    }

}

var aMessagesPanelBuilder = new MessagesPanelBuilder(
    configuration,
    systemsBuilder,
    {
        messages: [
            {from: "A", to: "B", description: "message from A to B"},
            {from: "A", to: "C", description: "message from A to C"},
            {from: "C", to: "B", description: "message from A to C"},
        ]
    },
    "anId",
    new DistancesCalculator()
)

QUnit.test("a messagePanelBuilder can be draw", function (assert) {

    aMessagesPanelBuilder.draw(svgContainer)

    assert.ok(true)

})
