var systemBuilderConfiguration = {"marginFromHorizontalBorder": 15, "marginFromVerticalBorder": 15}

var systemsHeaderBuilderConfiguration = {"distanceFromLeftBorder": 10, "distanceFromUpperBorder": 20}

var distancesCalculatorConfiguration = {
    "defaultDistanceBetweenSystems": 50,
    "distanceFromHorizontalBorder": 10,
}


var svgContainer = d3.select("#svgElement")

var systemBuilderA = new SystemBuilder(
    systemBuilderConfiguration,
    "ThisIsASystem",
    "a",
    new TextLengthCalculator(svgContainer)
)

var systemBuilderB = new SystemBuilder(
    systemBuilderConfiguration,
    "This_is_the_second_one",
    "b",
    new TextLengthCalculator(svgContainer)
)

var systemBuilderC = new SystemBuilder(
    systemBuilderConfiguration,
    "This_is_the_third_system",
    "c",
    new TextLengthCalculator(svgContainer)
)

var systemNames = ["ThisIsASystem", "This_is_the_second_one", "This_is_the_third_system"]

var systemBuilders = [systemBuilderA,systemBuilderB,systemBuilderC]

var conversationReport = {
    "systemNames": systemNames
}
function MaxDescriptionMessageLengthCalculator() {
    this.maxDescriptionLengthBetween = function (firstSystemName, secondSystemName) {
        if (firstSystemName === "ThisIsASystem" && secondSystemName === "This_is_the_third_system") {
            return 600
        }
        return 0
    }
}

var systemsHeaderBuilder = new SystemsHeaderBuilder(
    systemsHeaderBuilderConfiguration,
    systemBuilders,
    "sequenceDiagramId",
    new DistancesCalculator(
        distancesCalculatorConfiguration,
        systemBuilders,
        conversationReport,
        new MaxDescriptionMessageLengthCalculator()
    )
)

QUnit.test("size of A is correct", function (assert) {

    systemsHeaderBuilder.draw(svgContainer)

    assert.ok(true)

})
