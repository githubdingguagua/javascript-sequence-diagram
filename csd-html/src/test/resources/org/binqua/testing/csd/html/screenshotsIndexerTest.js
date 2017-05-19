QUnit.test("given 3 features and given feature-0-scenario-0-step-0-screenshot-0 has 'Create an account scenario 0 step0screenshot0' on one of his title " +
           "then search 'Create an account scenario 0 step0screenshot0' returns right identifier", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"},
      {"id": "feature-2"}
    ]

    var indexer = new ScreenshotsIndexer(new NamingUtil(), featureList, new UrlFormatter())

    indexer.init()

    var actualSearchResult = indexer.search("Create an account scenario 0 step0screenshot0")

    assert.equal(actualSearchResult.length, 1);

    assert.equal(actualSearchResult[0].featureId, "feature-0");
    assert.equal(actualSearchResult[0].scenarioId, "scenario-0");
    assert.equal(actualSearchResult[0].stepId, "step-0");
    assert.equal(actualSearchResult[0].screenshotId, "0");
    assert.equal(actualSearchResult[0].titleId, "1");

});

QUnit.test("given 3 features and given feature-2-scenario-5-step-12-screenshot-2 has 'toDo-list/scenario5' on one of his url " +
           "then search 'toDo-list/scenario5' returns the right identifier", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"},
      {"id": "feature-2"}
    ]

    var indexer = new ScreenshotsIndexer(new NamingUtil(), featureList, new UrlFormatter())

    indexer.init()

    var actualSearchResult = indexer.search("http://localhost:9090/toDo-list/scenario5")

    assert.equal(actualSearchResult.length, 1);

    assert.equal(actualSearchResult[0].featureId, "feature-2");
    assert.equal(actualSearchResult[0].scenarioId, "scenario-5");
    assert.equal(actualSearchResult[0].stepId, "step-12");
    assert.equal(actualSearchResult[0].screenshotId, "2");
    assert.equal(actualSearchResult[0].titleId, 2);

});

QUnit.test("screenshotPageTitleById returns the right title", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"},
      {"id": "feature-2"}
    ]

    var indexer = new ScreenshotsIndexer(new NamingUtil(), featureList, new UrlFormatter())

    indexer.init()

    assert.equal(indexer.screenshotPageTitleById({scenarioId: "scenario-4", screenshotId: 2}), "to-do list scenario 4 step10screenshot2");
    assert.equal(indexer.screenshotPageTitleById({scenarioId: "scenario-5", screenshotId: 2}), "to-do list scenario 5 step12screenshot2");

});

QUnit.test("listOfUrls returns the right formatted urls", function( assert ) {

    var urlFormatter = {
        format: function (urlToBeFormatted){
            return urlToBeFormatted + "-Formatted"
        }
    }

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"},
      {"id": "feature-2"}
    ]

    var indexer = new ScreenshotsIndexer(new NamingUtil(), featureList, urlFormatter)

    indexer.init()

    var actualListOfUrls = indexer.listOfUrls()

    assert.equal(actualListOfUrls.length, 18);
    assert.equal(actualListOfUrls[0], "http://localhost:9090/register/single/scenario0-Formatted");
    assert.equal(actualListOfUrls[15], "http://localhost:9090/toDo-list/scenario4-Formatted");

});


QUnit.test("listOfTitles returns the right title", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"},
      {"id": "feature-2"}
    ]

    var indexer = new ScreenshotsIndexer(new NamingUtil(), featureList, new UrlFormatter())

    indexer.init()

    var actualListOfTitles = indexer.listOfTitles()

    assert.equal(actualListOfTitles.length, 18);
    assert.equal(actualListOfTitles[0], "Create an account scenario 0 step0screenshot0");
    assert.equal(actualListOfTitles[15], "to-do list scenario 1");

});

