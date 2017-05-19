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

var screenshotsIndexer = {
    screenshotPageTitleById: function(input){
        return "the title of the screenshot " + input.screenshotId + " scenario id " + input.scenarioId
    }
}

var screenshotSearchResultFormatter = new ScreenshotSearchResultFormatter(smartBodyIndexer, screenshotsIndexer, new SearchResultLabelUtil(), new HtmlIdManager())

QUnit.test("search list is created correctly", function( assert ) {

    var allSearchResults = {
            featureId: "feature-1",
            scenarioId: "scenario-2",
            stepId: "step-3",
            screenshotId: 4,
            titleId: 10
    }

    assert.equal(
        screenshotSearchResultFormatter.format(0, allSearchResults),
        "<li data-screenshot-id='4' id='screenshot-search-link_feature-1_scenario-2_step-3_title-10'>" +
        "<a href='#screenshots-label_scenario-2_step-3'>" +
        "<span class='search-result-feature'>1) Feature 1:</span> <span>Feature desc feature-1</span>" +
        "<div><span class='search-result-scenario'>Scenario 2:</span> <span>Scenario desc scenario-2</span></div>" +
        "<div class='search-result-step-container'><span class='search-result-step'>Step 3:</span> <span>Step desc step-3</span></div>" +
        "<div class='search-result-screenshot-container'><span class='search-result-screenshot'>Title 10:</span> <span>the title of the screenshot 4 scenario id scenario-2</span></div>" +
        "</a>" +
        "</li>"
    );

});

QUnit.test("li id is parsed correctly", function( assert ) {

    var actualResult = screenshotSearchResultFormatter.toSearchScreenshotId("screenshot-search-link_feature-1_scenario-2_step-3_title-5")

    var expectedResult = {object:{
                                    id:'feature-1',
                                    searchResult:{
                                        thumbnailsContainerId:'thumbnail_scenario-2_step-3',
                                        screenshotImageId:'scenario-2_step-3_title-5'
                                    },
                                    selectedStep:{featureId:'feature-1', scenarioId:'scenario-2', stepId:'step-3'}
                                  }
                          }

    assert.deepEqual(actualResult, expectedResult);

});
