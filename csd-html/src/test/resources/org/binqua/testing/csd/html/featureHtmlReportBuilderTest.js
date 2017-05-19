QUnit.test("a full conversation can be adapted", function( assert ) {

    var featureIndexHtmlWithStepPlaceholders =   "<html>" +
                                                 "<body>" +
                                                 "<div>" +
                                                 "<div id='step-1-details'></div>" +
                                                 "<div></div>" +
                                                 "<div id='step-2-details'></div>" +
                                                 "<div></div>" +
                                                 "<div id='step-3-details'></div>" +
                                                 "<div></div>" +
                                                 "<div id='step-4-details'></div>" +
                                                 "</div>" +
                                                 "</body>" +
                                                 "</html>";

    var expectedFeatureHtmlWithoutPlaceholders =  "<div>" +
                                                  "<div>screenshot and sequence diagram for step 1</div>" +
                                                  "<div></div>" +
                                                  "<div>screenshot and sequence diagram for step 2</div>" +
                                                  "<div></div>" +
                                                  "<div>screenshot and sequence diagram for step 3</div>" +
                                                  "<div></div>" +
                                                  "<div>screenshot and sequence diagram for step 4</div>" +
                                                  "</div>" ;

    var scenarioConversation = {}
    var featureToBeShown = {}

    var artifactLoader = {
       featureIndexFile: function (featureToBeShown){}
    }

    var artifactLoaderStub = sinon.stub(artifactLoader, "featureIndexFile").withArgs(featureToBeShown).returns(featureIndexHtmlWithStepPlaceholders);

    var conversationAdapter = {
       adapt: function (conversationToBeAdapted){}
    }

    var featureConversationsByScenario = [ { "scenario-0": {"k": 0} }, { "scenario-1": {"k": 1} } ];

    var featureConversationsByStep = [
                                        {"step":"step-1"},
                                        {"step":"step-2"},
                                        {"step":"step-3"},
                                        {"step":"step-4"},
                                     ];

    var conversationAdapterStub = sinon.stub(conversationAdapter, "adapt").withArgs(featureConversationsByScenario).returns(featureConversationsByStep);

    var conversationRenderer = {
       toHtml: function (scenarioConversation, featureToBeShown){}
    }

    var conversationRendererStub = sinon.stub(conversationRenderer, "toHtml");

    conversationRendererStub.withArgs({"step":"step-1"}, featureToBeShown).returns("<div>screenshot and sequence diagram for step 1</div>");
    conversationRendererStub.withArgs({"step":"step-2"}, featureToBeShown).returns("<div>screenshot and sequence diagram for step 2</div>");
    conversationRendererStub.withArgs({"step":"step-3"}, featureToBeShown).returns("<div>screenshot and sequence diagram for step 3</div>");
    conversationRendererStub.withArgs({"step":"step-4"}, featureToBeShown).returns("<div>screenshot and sequence diagram for step 4</div>");

    var featureHtmlReportBuilder = new FeatureHtmlReportBuilder(conversationRenderer, artifactLoader, conversationAdapter)

    var actualNewFeatureHtml = featureHtmlReportBuilder.build(featureToBeShown, featureConversationsByScenario)

    assert.equal(actualNewFeatureHtml, expectedFeatureHtmlWithoutPlaceholders);

 });