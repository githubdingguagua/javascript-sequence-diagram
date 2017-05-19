var stepConversationAdapter = new StepConversationAdapter()

QUnit.test("a conversation with screenshots pairing with step conversation can be adapted", function( assert ) {

    var singleScenarioConversationToBeAdapted = {
                                                    "scenario": {"k": 0},
                                                    "screenshots": [
                                                        {"step": "step-1", "data": [{"url": "1"}]},
                                                        {"step": "step-2", "data": [{"url": "2"}]}
                                                    ],
                                                    "conversation": [
                                                        {"step": "step-1", "messages": [{"k": 0}, {"k": 1}], "sequenceDiagram": "svg0"},
                                                        {"step": "step-2", "messages": [{"k": 2}, {"k": 3}], "sequenceDiagram": "svg1"}
                                                    ]
                                                };

     var expectedAdaptedConversation = {
                                         "step":"step-1",
                                         "scenario": {"k": 0},
                                         "screenshots": [{"url": "1"}],
                                         "conversation":{"messages": [{"k": 0}, {"k": 1}],"sequenceDiagram": "svg0"}
                                       };

     assert.deepEqual(stepConversationAdapter.adapt(singleScenarioConversationToBeAdapted, "step-1"), expectedAdaptedConversation);

});

QUnit.test("a conversation with screenshots but empty step conversations can be adapted", function( assert ) {

    var singleScenarioConversationToBeAdapted = {
                    "scenario": {"k": 0},
                    "screenshots": [
                        {"step": "step-1", "data": [{"url": "1"}]},
                        {"step": "step-2", "data": [{"url": "2"}]}
                    ],
                    "conversation": [
                        {"step": "step-1", "messages": [], "sequenceDiagram": ""},
                        {"step": "step-2", "messages": [], "sequenceDiagram": ""}
                    ]
                };

     var expectedAdaptedConversation = {
                 "step":"step-1",
                 "scenario": {"k": 0},
                 "screenshots": [{"url": "1"}],
                 "conversation":{"messages": [],"sequenceDiagram": ""}
            };

     assert.deepEqual(stepConversationAdapter.adapt(singleScenarioConversationToBeAdapted, "step-1"), expectedAdaptedConversation);

});


QUnit.test("a conversation with empty conversations can be adapted", function( assert ) {

    var singleScenarioConversationToBeAdapted = {
                    "scenario": {"k": 0},
                    "screenshots": [
                        {"step": "step-1", "data": [{"url": "1"}]},
                        {"step": "step-2", "data": [{"url": "2"}]}
                    ],
                    "conversation": []
                };

     var expectedAdaptedConversation = {
                 "step":"step-1",
                 "scenario": {"k": 0},
                 "screenshots": [{"url": "1"}],
                 "conversation":{"messages": [],"sequenceDiagram": ""}
            };

     assert.deepEqual(stepConversationAdapter.adapt(singleScenarioConversationToBeAdapted, "step-1"), expectedAdaptedConversation);

});


QUnit.test("a conversation with empty screenshots but non empty step conversations can be adapted", function( assert ) {

    var singleScenarioConversationToBeAdapted = {
                                                    "scenario": {"k": 10},
                                                    "screenshots": [
                                                        {"step": "step-1", "data": []},
                                                        {"step": "step-2", "data": []}
                                                    ],
                                                    "conversation": [
                                                       {"step": "step-1", "messages": [{"k": 0}, {"k": 1}], "sequenceDiagram": "svg0"},
                                                       {"step": "step-2", "messages": [{"k": 2}, {"k": 3}], "sequenceDiagram": "svg2"}
                                                    ]
                                                };

     var expectedAdaptedConversation = {
                                         "step":"step-2",
                                         "scenario": {"k": 10},
                                         "screenshots": [],
                                         "conversation":{"messages": [{"k": 2}, {"k": 3}],"sequenceDiagram": "svg2"}
                                       };

     assert.deepEqual(stepConversationAdapter.adapt(singleScenarioConversationToBeAdapted, "step-2"), expectedAdaptedConversation);

});

QUnit.test("a conversation with empty screenshots and empty step conversation can be adapted", function( assert ) {

    var singleScenarioConversationToBeAdapted = {
                                      "scenario": {"k": 0},
                                      "screenshots": [{"step": "step-1", "data": []}],
                                      "conversation": [{"step": "step-1", "messages": [], "sequenceDiagram": ""}]
                                  };

    var expectedAdaptedConversation = {
                                        "step":"step-1",
                                        "scenario": {"k": 0},
                                        "screenshots": [],
                                        "conversation":{"messages": [],"sequenceDiagram": ""}
                                      };

     assert.deepEqual(stepConversationAdapter.adapt(singleScenarioConversationToBeAdapted, "step-1"), expectedAdaptedConversation);

});
