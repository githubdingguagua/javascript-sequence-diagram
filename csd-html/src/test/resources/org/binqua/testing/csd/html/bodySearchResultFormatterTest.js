 var smartBodyIndexer = {
        featureNameById: function(featureId){
            return "Feature desc " + featureId
        },
        scenarioNameById: function(scenarioId){
            return "Scenario desc " + scenarioId
        },
        stepNameById: function(stepId){
            return "Step desc " + stepId
        }
    }

var bodySearchResultFormatter = new BodySearchResultFormatter(smartBodyIndexer, new SearchResultLabelUtil());

QUnit.test("given 2 configurations with steps then search result list is created correctly", function( assert ) {

    var searchResultsToBeFormatted = { featureId:"feature-1", scenarioId:"scenario-2", stepId:"step-3" }

    var expectedSearchResultsFormatted = "<li id='body-search-link_feature-1_scenario-2_step-3'>" +
    "<a href='#step-3'><span class='search-result-feature'>1) Feature 1:</span> <span>Feature desc feature-1</span>" +
    "<div><span class='search-result-scenario'>Scenario 2:</span> <span>Scenario desc scenario-2</span></div>" +
    "<div class='search-result-step-container'><span class='search-result-step'>Step 3:</span> <span>Step desc step-3</span></div>" +
    "</a></li>";

    assert.equal(bodySearchResultFormatter.format(0, searchResultsToBeFormatted), expectedSearchResultsFormatted);

});

QUnit.test("given 1 configurations with featureId, scenarioId and no stepId then search result list is created correctly", function( assert ) {

    var searchResultsToBeFormatted = { featureId:"feature-1", scenarioId:"scenario-2", stepId:"" };

    var expectedSearchResultsFormatted = "<li id='body-search-link_feature-1_scenario-2'>" +
    "<a href='#scenario-2'><span class='search-result-feature'>1) Feature 1:</span> <span>Feature desc feature-1</span>" +
    "<div><span class='search-result-scenario'>Scenario 2:</span> <span>Scenario desc scenario-2</span></div>" +
    "</a></li>";

    assert.equal(bodySearchResultFormatter.format(0, searchResultsToBeFormatted), expectedSearchResultsFormatted);

});

QUnit.test("given 1 configurations with featureId no scenarioId and no stepId then search result list is created correctly", function( assert ) {

    var searchResultsToBeFormatted = { featureId:"feature-1", scenarioId:"", stepId:"" };

    var expectedSearchResultsFormatted = "<li id='body-search-link_feature-1'><a href='#feature-1'><span class='search-result-feature'>1) Feature 1:</span> <span>Feature desc feature-1</span></a></li>"

    assert.equal(bodySearchResultFormatter.format(0, searchResultsToBeFormatted), expectedSearchResultsFormatted);

});

QUnit.test("li id is parsed correctly in case of a featureId", function( assert ) {

    var actualResult = bodySearchResultFormatter.toSearchResultId("body-search-link-0_feature-1")

    var expectedResult = {object:{ id:'feature-1', searchResult:{idFound:'feature-1'}}}

    assert.deepEqual(actualResult, expectedResult);

});

QUnit.test("li id is parsed correctly in case of a scenarioId", function( assert ) {

    var actualResult = bodySearchResultFormatter.toSearchResultId("body-search-link_feature-1_scenario-2")

    var expectedResult = {object:{ id:'feature-1', searchResult:{idFound:'scenario-2'}}}

    assert.deepEqual(actualResult, expectedResult);

});

QUnit.test("li id is parsed correctly in case of a stepId", function( assert ) {

    var actualResult = bodySearchResultFormatter.toSearchResultId("body-search-link_feature-1_scenario-2_step-3")

    var expectedResult = {object:{ id:'feature-1', searchResult:{idFound:'step-3'}}}

    assert.deepEqual(actualResult, expectedResult);

});


