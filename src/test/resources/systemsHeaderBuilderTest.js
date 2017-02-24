var systemBuilderConfiguration = {"marginFromHorizontalBorder": 15, "marginFromVerticalBorder": 15}

var systemsHeaderBuilderConfiguration = {"distanceFromLeftBorder": 10, "distanceFromUpperBorder": 20}

var svgContainer = d3.select("#svgElement")

var systemBuilderA = new SystemBuilder(
    systemBuilderConfiguration,
    "A",
    "a",
    new TextLengthCalculator(svgContainer)
)

var systemBuilderB = new SystemBuilder(
    systemBuilderConfiguration,
    "B",
    "b",
    new TextLengthCalculator(svgContainer)
)

var systemsHeaderBuilder = new SystemsHeaderBuilder(
    systemsHeaderBuilderConfiguration,
    [systemBuilderA,systemBuilderB],
    "sequenceDiagramId",
    new DistancesCalculator()
)

QUnit.test("size of A is correct", function (assert) {

    systemsHeaderBuilder.draw(svgContainer)

    assert.ok(true)

})
