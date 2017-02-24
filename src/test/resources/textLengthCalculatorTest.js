var textLengthCalculator = new TextLengthCalculator(d3.select("#svgElement"))

QUnit.test("size of A is correct", function (assert) {

    var sizeOfA = textLengthCalculator.sizeOf("A")

    assert.deepEqual(sizeOfA, {"width": 9.328125, "height": 16})

})

QUnit.test("size of AA is correct", function (assert) {

    var sizeOfAA = textLengthCalculator.sizeOf("AA")

    assert.deepEqual(sizeOfAA, {"width": 18.671875, "height": 16})

})

