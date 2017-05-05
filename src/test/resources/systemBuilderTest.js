var configuration = {"marginFromHorizontalBorder": 15, "marginFromVerticalBorder": 15}

var svgContainer = d3.select("#svgElement")

var systemBuilder = new SystemBuilder(
    configuration, "ThisIsA.System.That.Send.Messages",
    "anId",
    new TextLengthCalculator(svgContainer)
)

QUnit.test("systemBuilder can be draw", function (assert) {

    systemBuilder.draw(svgContainer)

    assert.ok(true)

})
