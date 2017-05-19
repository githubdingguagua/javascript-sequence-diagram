QUnit.test("given showSequenceDiagram is false then html is an empty string", function( assert ) {

    var showSequenceDiagram = false

    assert.equal(new ConversationMessagesRenderer(showSequenceDiagram).render(), "");

});

QUnit.test("given a conversation with a request and a response but not delivery exceptions then html is correct", function( assert ) {

    var theStepConversationToBeRendered = {
                                              "messages": [
                                                  {
                                                      "id": "request-1",
                                                      "correlationId": "1",
                                                      "type": "request",
                                                      "from": "A",
                                                      "to": {"url": "u", "alias": "B", "description": "description1"},
                                                      "path": {
                                                          "value": "/status",
                                                          "alias": "status"
                                                      },
                                                      "method": "GET",
                                                      "description": "status Request from A to B",
                                                      "headers": {
                                                          'http-headers': {'key1': 'value1'}
                                                      },
                                                      "body": "requestBody"
                                                  },
                                                  {
                                                      "id": "response-2",
                                                      "correlationId": "1",
                                                      "type": "response",
                                                      "from": "B",
                                                      "to": "A",
                                                      "description": "status Response from B to A",
                                                      "headers": {
                                                          'http-headers': {'key2': 'value2'}
                                                      },
                                                      "body": "responseBody"
                                                  }
                                              ],
                                              "sequenceDiagram": "<svg></svg>"
                                          }

    var showSequenceDiagram = true

    var expectedHtml =  "<div class='conversations-messages'>" +
                           "<p class='conversations-messages-title'>Messages:</p>" +
                           "<div class='conversations-body'>" +
                               "<div class='conversation-request'>" +
                                   "<p class='conversation-title conversation-request-title' id='request-1-title' >status Request from A to B</p>" +
                                   "<ol class='conversation-request-attributes'>" +
                                   "<li class='attribute'><span class='attribute-key'>method</span><span class='attribute-separator'> : </span><span class='attribute-value'>GET</span></li>" +
                                   "<li class='attribute'><span class='attribute-key'>path</span><span class='attribute-separator'> : </span><span class='attribute-value'>/status</span></li>" +
                                   "<li class='attribute'><span class='attribute-key'>key1</span><span class='attribute-separator'> : </span><span class='attribute-value'>value1</span></li>" +
                                   "<li><span>Body:</span><div id='request-1-body' class='request-body'></div></li>" +
                                   "</ol>" +
                               "</div>" +
                               "<div class='conversation-response'>" +
                                   "<p class='conversation-title conversation-response-title' id='response-2-title' >status Response from B to A</p>" +
                                   "<ol class='conversation-response-attributes'>" +
                                   "<li class='attribute'><span class='attribute-key'>key2</span><span class='attribute-separator'> : </span><span class='attribute-value'>value2</span></li>" +
                                   "<li><span>Body:</span><div id='response-2-body' class='response-body'></div></li>" +
                                   "</ol>"+
                               "</div>" +
                           "</div>" +
                       "</div>" +
                       "<div class='conversations-sequence-diagram'>" +
                           "<p class='conversations-sequence-diagram-title'>Sequence Diagram:</p>" +
                           "<div class='conversations-sequence-diagram-body'>" +
                               "<svg></svg>" +
                           "</div>" +
                       "</div>"

    assert.equal(new ConversationMessagesRenderer(showSequenceDiagram).render(theStepConversationToBeRendered), expectedHtml);

});


QUnit.test("given a conversation with a delivery exception then html contains the body and the exception", function( assert ) {

    var theStepConversationToBeRendered = {
                                              "messages": [
                                                  {
                                                      "id": "request-1",
                                                      "correlationId": "1",
                                                      "type": "request",
                                                      "from": "A",
                                                      "to": {"url": "u", "alias": "B", "description": "description1"},
                                                      "path": {
                                                          "value": "/status",
                                                          "alias": "status"
                                                      },
                                                      "method": "GET",
                                                      "description": "status Request from A to B",
                                                      "headers": {
                                                          'http-headers': {'key1': 'value1'}
                                                      },
                                                      "body": "requestBody",
                                                      "deliveryException": "something went wrong"
                                                  }
                                              ],
                                              "sequenceDiagram": "<svg></svg>"
                                          }

    var showSequenceDiagram = true

    var expectedHtml =  "<div class='conversations-messages'>" +
                           "<p class='conversations-messages-title'>Messages:</p>" +
                           "<div class='conversations-body'>" +
                               "<div class='conversation-request'>" +
                                   "<p class='conversation-title conversation-request-title' id='request-1-title' >status Request from A to B</p>" +
                                   "<ol class='conversation-request-attributes'>" +
                                   "<li class='attribute'><span class='attribute-key'>method</span><span class='attribute-separator'> : </span><span class='attribute-value'>GET</span></li>" +
                                   "<li class='attribute'><span class='attribute-key'>path</span><span class='attribute-separator'> : </span><span class='attribute-value'>/status</span></li>" +
                                   "<li class='attribute'><span class='attribute-key'>key1</span><span class='attribute-separator'> : </span><span class='attribute-value'>value1</span></li>" +
                                   "<li><span>Body:</span><div id='request-1-body' class='request-body'></div></li>" +
                                   "<li><span>Delivery Exception:</span><div id='delivery-exception-request-1-body' class='delivery-exception-body'></div></li>" +
                                   "</ol>" +
                               "</div>" +
                           "</div>" +
                       "</div>" +
                       "<div class='conversations-sequence-diagram'>" +
                           "<p class='conversations-sequence-diagram-title'>Sequence Diagram:</p>" +
                           "<div class='conversations-sequence-diagram-body'>" +
                               "<svg></svg>" +
                           "</div>" +
                       "</div>"

    assert.equal(new ConversationMessagesRenderer(showSequenceDiagram).render(theStepConversationToBeRendered), expectedHtml);

});



QUnit.test("given a conversation with no messages then html is empty string", function( assert ) {

    var theStepConversationToBeRendered = { "messages": [] }

    var showSequenceDiagram = true

    var expectedHtml =  ""

    assert.equal(new ConversationMessagesRenderer(showSequenceDiagram).render(theStepConversationToBeRendered), expectedHtml);

});

QUnit.test("given a message with a request then message Html has the right value for body part id", function( assert ) {

    var messageToBeRendered =  {
                                  "id": "request-1",
                                  "correlationId": "1",
                                  "type": "request",
                                  "from": "A",
                                  "to": {"url": "u", "alias": "B", "description": "description1"},
                                  "description": "status Request from A to B",
                                  "body": "requestBody"
                               }

    var expectedHtml = "<div class='conversation-request'>" +
                           "<p class='conversation-title conversation-request-title' id='request-1-title' >status Request from A to B</p>" +
                           "<ol class='conversation-request-attributes'>" +
                           "<li><span>Body:</span><div id='request-1-bodyBodyEditorIdSuffix' class='request-body'></div></li>" +
                           "</ol>" +
                       "</div>"

    assert.equal(new ConversationMessagesRenderer().messageHtml(messageToBeRendered, "BodyEditorIdSuffix"), expectedHtml);

});

QUnit.test("given a message with no body then message Html has not the body div", function( assert ) {

    var messageToBeRendered =  {
                                  "id": "request-1",
                                  "correlationId": "1",
                                  "type": "request",
                                  "from": "A",
                                  "to": {"url": "u", "alias": "B", "description": "description1"},
                                  "description": "status Request from A to B",
                                  "body": {}
                               }

    var expectedHtml = "<div class='conversation-request'>" +
                           "<p class='conversation-title conversation-request-title' id='request-1-title' >status Request from A to B</p>" +
                           "<ol class='conversation-request-attributes'>" +
                           "</ol>" +
                       "</div>"

    assert.equal(new ConversationMessagesRenderer().messageHtml(messageToBeRendered), expectedHtml);

});




