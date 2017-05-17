QUnit.test("",
    function (assert) {

        var maxDescriptionMessageCalculator = {
            calculateDescriptionBetween: function () {
            }
        }

        var maxDescriptionLengthBetweenSystemsStub = sinon.stub(maxDescriptionMessageCalculator, "calculate");

        maxDescriptionLengthBetweenSystemsStub.withArgs("A", "B").returns("1")
        maxDescriptionLengthBetweenSystemsStub.withArgs("B", "A").returns("1")

        maxDescriptionLengthBetweenSystemsStub.withArgs("B", "C").returns("11")
        maxDescriptionLengthBetweenSystemsStub.withArgs("C", "B").returns("11")

        maxDescriptionLengthBetweenSystemsStub.withArgs("D", "E").returns("111")
        maxDescriptionLengthBetweenSystemsStub.withArgs("E", "D").returns("111")

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

        assert.equal(calculator.calculate("A", "B"), 1)
        assert.equal(calculator.calculate("B", "A"), 1)

        assert.equal(calculator.calculate("B", "C"), 2)
        assert.equal(calculator.calculate("C", "B"), 2)

        assert.equal(calculator.calculate("D", "E"), 3)
        assert.equal(calculator.calculate("E", "D"), 3)
    }
)

