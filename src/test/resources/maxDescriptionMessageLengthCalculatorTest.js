QUnit.test("given 2 systems and 4 messages with max description in a to-from  message then max description is correct",
    function (assert) {

        var longestDescription = "1234"

        var conversation = [
            {"from": "A", "to": "B", "description": "123"},
            {"from": "A", "to": "B", "description": longestDescription},
            {"from": "B", "to": "A", "description": "12"},
            {"from": "B", "to": "A", "description": "1"}
        ]

        var calculator = new MaxDescriptionMessageLengthCalculator(conversation)

        assert.equal(calculator.maxDescriptionLengthBetweenSystems("A", "B"), longestDescription)
        assert.equal(calculator.maxDescriptionLengthBetweenSystems("B", "A"), longestDescription)
    }
)

QUnit.test("given 2 systems and 4 messages with max description in a from-to message then max description is correct",
    function (assert) {

        var longestDescription = "1234"

        var conversation = [
            {"from": "A", "to": "B", "description": longestDescription},
            {"from": "A", "to": "B", "description": "123"},
            {"from": "B", "to": "A", "description": "12"},
            {"from": "B", "to": "A", "description": "1"}
        ]

        var calculator = new MaxDescriptionMessageLengthCalculator(conversation)

        assert.equal(calculator.maxDescriptionLengthBetweenSystems("A", "B"), longestDescription)
        assert.equal(calculator.maxDescriptionLengthBetweenSystems("B", "A"), longestDescription)
    }
)

QUnit.test("given 4 systems then max description is correct",
    function (assert) {

        var longestDescription = "1234"

        var conversation = [
            {"from": "A", "to": "B", "description": longestDescription},
            {"from": "A", "to": "B", "description": "3"},
            {"from": "B", "to": "C", "description": "4"},
            {"from": "C", "to": "D", "description": "5"},
            {"from": "D", "to": "E", "description": "6"},
        ]

        var calculator = new MaxDescriptionMessageLengthCalculator(conversation)

        assert.equal(calculator.maxDescriptionLengthBetweenSystems("A", "B"), longestDescription)
        assert.equal(calculator.maxDescriptionLengthBetweenSystems("B", "A"), longestDescription)

        assert.equal(calculator.maxDescriptionLengthBetweenSystems("B", "C"), 4)
        assert.equal(calculator.maxDescriptionLengthBetweenSystems("C", "B"), 4)

        assert.equal(calculator.maxDescriptionLengthBetweenSystems("C", "D"), 5)
        assert.equal(calculator.maxDescriptionLengthBetweenSystems("D", "C"), 5)

        assert.equal(calculator.maxDescriptionLengthBetweenSystems("D", "E"), 6)
        assert.equal(calculator.maxDescriptionLengthBetweenSystems("E", "D"), 6)

    }
)

QUnit.test("given a non existing system pair then max description is undefined",
    function (assert) {

        var conversation = [
            {"from": "A", "to": "B", "description": "1"}
        ]

        assert.equal(new MaxDescriptionMessageLengthCalculator(conversation).maxDescriptionLengthBetweenSystems("C", "D"), undefined)
    }
)



