var configuration = {distanceBetweenMessages: 30, distanceFromLeftBorder: 10}

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
        return 10 + 400 + 50 + 50 + 10
    }

    this.leftBorderDistanceBetweenFirstSystemAndSystem = function (nameOfTheSystemToAnalyse) {
        if (nameOfTheSystemToAnalyse == "A") return 0
        if (nameOfTheSystemToAnalyse == "B") return 200
        if (nameOfTheSystemToAnalyse == "C") return 400
    }

    this.middlePointXCoordinateOfSystem = function (nameOfTheSystemToAnalyse) {
        if (nameOfTheSystemToAnalyse == "A") return 10 + 50
        if (nameOfTheSystemToAnalyse == "B") return 10 + this.leftBorderDistanceBetweenFirstSystemAndSystem("B") + 50
        if (nameOfTheSystemToAnalyse == "C") return 10 + this.leftBorderDistanceBetweenFirstSystemAndSystem("C") + 50
    }

    function systemsAre(firstSystem, secondSystem, expectedFirstSystem, expectedSecondSystem) {
        return (firstSystem == expectedFirstSystem && secondSystem == expectedSecondSystem ) ||
            (firstSystem == expectedSecondSystem && secondSystem == expectedFirstSystem );
    }

    this.middlePointXCoordinateBetweenSystems = function (firstSystem, secondSystem) {
        if (systemsAre(firstSystem, secondSystem, "A", "B")) return (this.middlePointXCoordinateOfSystem("A") + (this.middlePointXCoordinateOfSystem("B") - this.middlePointXCoordinateOfSystem("A") ) / 2)
        if (systemsAre(firstSystem, secondSystem, "B", "C")) return (this.middlePointXCoordinateOfSystem("B") + (this.middlePointXCoordinateOfSystem("C") - this.middlePointXCoordinateOfSystem("B") ) / 2)
        if (systemsAre(firstSystem, secondSystem, "A", "C")) return (this.middlePointXCoordinateOfSystem("A") + (this.middlePointXCoordinateOfSystem("C") - this.middlePointXCoordinateOfSystem("A") ) / 2)
    }

}

var aMessagesPanelBuilder = new MessagesPanelBuilder(
    configuration,
    systemsBuilder,
    {
        messages: [
            {from: "A", to: "B", description: "message from A to B"},
            {from: "A", to: "C", description: "message from A to C"},
            {from: "C", to: "B", description: "message from A to C"}
        ]
    },
    "anId",
    new DistancesCalculator()
)

QUnit.test("a messagePanelBuilder can be draw", function (assert) {

    aMessagesPanelBuilder.draw(svgContainer)

    assert.ok(true)

})
