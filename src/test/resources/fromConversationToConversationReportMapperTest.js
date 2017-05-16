QUnit.test("given 4 messages 2 with a simple alias and 2 with a compost alias the messages are mapped correctly",
    function (assert) {

        var conversationToBeConverted = [
            {"from": "A", "to": "B", "description": "ab"},
            {"from": "B", "to": {"url": "u", "alias": "C", "description": "description1"}, "description": "bc"},
            {"from": "C", "to": "D", "description": "cd"},
            {"from": "E", "to": {"url": "u", "alias": "F", "description": "description1"}, "description": "ef"}
        ]

        var calculator = new FromConversationToConversationReportMapper()

        var actualConversationReport = calculator.map(conversationToBeConverted)

        assert.deepEqual(actualConversationReport.conversation, [
                {"from": "A", "to": "B", "description": "ab"},
                {"from": "B", "to": "C", "description": "bc"},
                {"from": "C", "to": "D", "description": "cd"},
                {"from": "E", "to": "F", "description": "ef"}
            ]
        )

        assert.deepEqual(actualConversationReport.systemNames, ["A", "B", "C", "D", "E", "F"])

    }
)
