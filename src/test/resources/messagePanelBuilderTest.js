var configuration = {"distanceBetweenMessages": 10}

var svgContainer = d3.select("#svgElement")

var systemsBuilder = [new SystemBuilder("A", 50 + 50), new SystemBuilder("B", 10 + 10), new SystemBuilder("C", 20 + 20)]

var aMessagePanelBuilder = new MessagePanelBuilder(
    configuration,
    systemsBuilder,
    {
        messages: [
            {from: "A", to: "B", description: "message from A to B"},
            {from: "A", to: "C", description: "message from A to C"}
        ]
    },
    "anId",
    new TextLengthCalculator(svgContainer)
)

QUnit.test("a messagePanelBuilder can be draw", function (assert) {

    aMessagePanelBuilder.draw(svgContainer)

    assert.ok(true)

})
