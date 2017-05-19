var messagesSupport = new MessagesSupport(new NamingUtil());

QUnit.test("messagesSupport can record a conversation and can return the list of all message bodies", function( assert ) {

     var singleScenarioConversation = {
                            "scenario": {
                              "name": "scenario-0",
                              "id": "scenario-0"
                            },
                            "screenshots": [
                              {"step": "step-1","data": []},
                              {"step": "step-2","data": []}
                            ],
                            "conversation": [
                              {
                                "step": "step-1",
                                "messages": [{
                                    "id": "request-1",
                                    "correlationId": "1",
                                    "type": "request",
                                    "headers": {
                                        'http-headers': {'key1': 'value1'}
                                    },
                                    "body": {"value": "requestBody", "content-type": "json"}
                                }],
                                "sequenceDiagram": "<svg>do this first step</svg>"
                              },
                              {
                                "step": "step-2",
                                "messages": [{
                                    "id": "response-2",
                                    "correlationId": "1",
                                    "type": "response",
                                    "headers": {
                                        'http-headers': {'key2': 'value2'}
                                    },
                                    "body": {"value": "responseBody", "content-type": "xml"},
                                    "deliveryException": "some exception message"
                                }],
                                "sequenceDiagram": "<svg>do this second step</svg>"
                              }
                            ]
                          }

        function assertEntryIs(actualEntry, expectedEntry) {
            equal(actualEntry.key, expectedEntry.key);
            equal(actualEntry.body.value, expectedEntry.body.value);
            equal(actualEntry.body["content-type"], expectedEntry.body["content-type"]);
        }

        messagesSupport.recordConversation(singleScenarioConversation);

        equal(messagesSupport.messagesBody().length, 2);

        assertEntryIs(messagesSupport.messagesBody()[0], {
            "key": "request-1-body",
            "body": {"value": "requestBody", "content-type": "json"},
            "deliveryException" : ""
        });
        assertEntryIs(messagesSupport.messagesBody()[1], {
            "key": "response-2-body",
            "body": {"value": "responseBody", "content-type": "xml"},
            "deliveryException" : "some exception message"
        });

});

QUnit.test("messagesSupport can record a conversation and retrieve messages by id", function () {

    var singleScenarioConversation = {
                                        "scenario": {
                                          "name": "scenario-0",
                                          "id": "scenario-0"
                                        },
                                        "screenshots": [
                                          {"step": "step-1","data": []},
                                          {"step": "step-2","data": []}
                                        ],
                                        "conversation": [
                                          {
                                            "step": "step-1",
                                            "messages": [{
                                                "id": "request-1",
                                                "k1": "v1"
                                            }],
                                            "sequenceDiagram": "<svg>do this first step</svg>"
                                          },
                                          {
                                            "step": "step-2",
                                            "messages": [{
                                                "id": "response-2",
                                                "k2": "v2"
                                            }],
                                            "sequenceDiagram": "<svg>do this second step</svg>"
                                          }
                                        ]
                                      }

    messagesSupport.recordConversation(singleScenarioConversation);

    equal(messagesSupport.message("request-1")["k1"], "v1");

    equal(messagesSupport.message("response-2")["k2"], "v2");

});