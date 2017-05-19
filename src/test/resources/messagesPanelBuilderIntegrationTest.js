var svgContainer = d3.select("#svgElement")

var configuration = {marginFromHorizontalBorder: 10, marginFromVerticalBorder: 10}

var textLengthCalculator = new TextLengthCalculator(svgContainer)

var systemA = new SystemBuilder(configuration, "A", "a", textLengthCalculator)
var systemB = new SystemBuilder(configuration, "B", "b", textLengthCalculator)
var systemC = new SystemBuilder(configuration, "C", "c", textLengthCalculator)
var systemD = new SystemBuilder(configuration, "D", "d", textLengthCalculator)

var systemBuilders = [systemA, systemB, systemC, systemD]

var distancesCalculator = new DistancesCalculator(configuration, systemsBuilder, conversationReport, messageLengthCalculator)

var aMessagesPanelBuilder = new MessagesPanelBuilder(
    configuration,
    systemBuilders,
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
