QUnit.test('a feature conversation by scenarios can be adapted to a x step conversation by delegating to stepConversationAdapter', function( assert ) {

    var stepConversationAdapter = {
       adapt: function (singleScenarioConversation, stepId){}
    }

    var conversationAdapter = new ConversationAdapter(stepConversationAdapter)
    var firstScenario = {
                            'scenario': {'k': 0},
                            'screenshots': [
                                {'step': 'step-1', 'data': [{'url': '1'}]},
                                {'step': 'step-2', 'data': [{'url': '2'}]}
                            ],
                            'conversation': [
                                {'step': 'step-1', 'messages': [{'k': 0}], 'sequenceDiagram': 'svg0'},
                                {'step': 'step-2', 'messages': [{'k': 2}], 'sequenceDiagram': 'svg1'}
                            ]
                        }

    var secondScenario = {
                             'scenario': {'k': 1},
                             'screenshots': [
                                 {'step': 'step-3', 'data': [{'url': '3'}]},
                                 {'step': 'step-4', 'data': [{'url': '4'}]}
                             ],
                             'conversation': [
                                 {'step': 'step-5', 'messages': [{'k': 5}], 'sequenceDiagram': 'svg5'},
                                 {'step': 'step-6', 'messages': [{'k': 6}], 'sequenceDiagram': 'svg6'}
                             ]
                         }
    var featureConversationStructuredByScenarios = [];
    featureConversationStructuredByScenarios.push(firstScenario)
    featureConversationStructuredByScenarios.push(secondScenario)

    var stepConversationAdapterStub = sinon.stub(stepConversationAdapter, 'adapt');

    stepConversationAdapterStub.withArgs(firstScenario, 'step-1').returns({'k':1})
    stepConversationAdapterStub.withArgs(firstScenario, 'step-2').returns({'k':2})
    stepConversationAdapterStub.withArgs(secondScenario, 'step-3').returns({'k':3})
    stepConversationAdapterStub.withArgs(secondScenario, 'step-4').returns({'k':4})
    stepConversationAdapterStub.withArgs(secondScenario, 'step-5').returns({'k':5})
    stepConversationAdapterStub.withArgs(secondScenario, 'step-6').returns({'k':6})

    var expectedFeatureConversationStructuredBySteps = [
        {'k':1},
        {'k':2},
        {'k':3},
        {'k':4},
        {'k':5},
        {'k':6}
    ];

    assert.deepEqual(conversationAdapter.adapt(featureConversationStructuredByScenarios), expectedFeatureConversationStructuredBySteps);

});

QUnit.test('given an empty conversation a feature conversation by scenarios can be adapted to a x step conversation', function( assert ) {

    var stepConversationAdapter = {
       adapt: function (singleScenarioConversation, stepId){}
    }

    var conversationAdapter = new ConversationAdapter(stepConversationAdapter)

    var firstScenario = {
                            'scenario': {'k': 0},
                            'screenshots': [ {'step': 'step-1', 'data': [{'url': '1'}]} ],
                            'conversation': []
                        }

    var secondScenario = {
                             'scenario': {'k': 1},
                             'screenshots': [ {'step': 'step-3', 'data': [{'url': '3'}]} ],
                             'conversation': []
                         }
    var featureConversationStructuredByScenarios = [];
    featureConversationStructuredByScenarios.push(firstScenario)
    featureConversationStructuredByScenarios.push(secondScenario)

    var stepConversationAdapterStub = sinon.stub(stepConversationAdapter, 'adapt');

    stepConversationAdapterStub.withArgs(firstScenario, 'step-1').returns({'k':1})
    stepConversationAdapterStub.withArgs(secondScenario, 'step-3').returns({'k':3})

    var expectedFeatureConversationStructuredBySteps = [
        {'k':1},
        {'k':3}
    ];

    assert.deepEqual(conversationAdapter.adapt(featureConversationStructuredByScenarios), expectedFeatureConversationStructuredBySteps);

});