QUnit.test("calculate delegates to the text length calculator to calculates the size of the text",
    function (assert) {

        var maxDescriptionMessageCalculator = {
                calculate: function () {
            }
        }

        var maxDescriptionMessageCalculatorStub = sinon.stub(maxDescriptionMessageCalculator, "calculate");

        maxDescriptionMessageCalculatorStub.withArgs("A", "B").returns("1")
        maxDescriptionMessageCalculatorStub.withArgs("B", "A").returns("1")

        maxDescriptionMessageCalculatorStub.withArgs("B", "C").returns("11")
        maxDescriptionMessageCalculatorStub.withArgs("C", "B").returns("11")

        maxDescriptionMessageCalculatorStub.withArgs("D", "E").returns("111")
        maxDescriptionMessageCalculatorStub.withArgs("E", "D").returns("111")

        var textLengthCalculator = {
            sizeof: function () {
            }
        }

        var sizeofStub = sinon.stub(textLengthCalculator, "sizeof")

        sizeofStub.withArgs("1").returns({"width": 1, "height": 0})

        sizeofStub.withArgs("11").returns({"width": 2, "height": 0})

        sizeofStub.withArgs("111").returns({"width": 3, "height": 0})

        var calculator = messageLengthCalculator(
            maxDescriptionMessageCalculator,
            textLengthCalculator
        )

        assert.equal(calculator.calculateMaxMessageLengthBetweenSystems("A", "B"), 1)
        assert.equal(calculator.calculateMaxMessageLengthBetweenSystems("B", "A"), 1)

        assert.equal(calculator.calculateMaxMessageLengthBetweenSystems("B", "C"), 2)
        assert.equal(calculator.calculateMaxMessageLengthBetweenSystems("C", "B"), 2)

        assert.equal(calculator.calculateMaxMessageLengthBetweenSystems("D", "E"), 3)
        assert.equal(calculator.calculateMaxMessageLengthBetweenSystems("E", "D"), 3)
    }
)

